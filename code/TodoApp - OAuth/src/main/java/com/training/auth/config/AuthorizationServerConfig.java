package com.training.auth.config;

import com.training.auth.service.CustomAuthenticationKeyGenerator;
import com.training.auth.service.CustomTokenEnhancer;
import com.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.time.Duration;
import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final RedisConnectionFactory connectionFactory;

    private final CustomAuthenticationKeyGenerator customAuthenticationKeyGenerator;

    @Autowired
    public AuthorizationServerConfig(@Qualifier("authenticationManagerBean") AuthenticationManager authenticationManager,
                                     UserService userService, RedisConnectionFactory redisConnectionFactory,
                                     CustomAuthenticationKeyGenerator customAuthenticationKeyGenerator) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.connectionFactory = redisConnectionFactory;
        this.customAuthenticationKeyGenerator = customAuthenticationKeyGenerator;
    }

    /**
     * Instantiates the Redis token store.
     *
     * @return the new Redis token store
     */
    @Bean
    public TokenStore tokenStore() {
        RedisTokenStore tokenStore = new RedisTokenStore(connectionFactory);
        tokenStore.setAuthenticationKeyGenerator(customAuthenticationKeyGenerator);
        return tokenStore;
    }

    /**
     * Instantiates the custom token enhancer used.
     *
     * @return the new custom token enhancer
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }

    /**
     * Enhances the token store to use Asymmetric JWTs.
     * if this is not used the simple token will be send as response eg: 7d303eb7-a743-4f6f-acb0-89a5984853f3
     *
     * @return the newly created JwtAccessTokenConverter
     */
    @Bean
    public JwtAccessTokenConverter jwtTokenEnhancer() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                new ClassPathResource("qualityCheck.jks"),
                "zS3zgdDlnPHkecQvEbi8".toCharArray());
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("rosswell.jwt"));
        return converter;
    }

    /**
     * Configures the clients that are allowed to authenticate.
     * 'browser' is the basic auth that need to be send with each login
     * authorizedGrantTypes are the supported grant types supported by the server
     *
     * @param clients the client configurer object
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("browser")
                .accessTokenValiditySeconds((int) Duration.ofDays(1).toSeconds())
                .refreshTokenValiditySeconds((int) Duration.ofDays(30).toSeconds())
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("ui");
    }


    /**
     * Configures the token store, enhancer, converter, user details service and authentication manager to use.
     *
     * @param endpoints the AuthorizationServerEndpointsConfigurer object that allows us to set these configurations
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtTokenEnhancer()));
        endpoints
                .tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancerChain)
                .accessTokenConverter(jwtTokenEnhancer())
                .authenticationManager(authenticationManager)
                .userDetailsService(userService)
                .reuseRefreshTokens(false);
    }

    /**
     * Configures the oauth server permissions.
     *
     * @param oauthServer the oauth server
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()").passwordEncoder(new BCryptPasswordEncoder());
    }
}