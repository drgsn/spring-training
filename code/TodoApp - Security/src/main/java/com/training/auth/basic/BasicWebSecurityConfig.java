package com.training.auth.basic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

/**
 * To activate it, add this annotations
 */
//@Configuration
//@EnableWebSecurity
//// user for @PreAuthorize / @PostAuthorize / @Secured
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class BasicWebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * As Thymeleaf has good integration with Spring Security (when used together with Spring Boot),
     * you can simply add the following snippet to any form and you’ll get the token injected automatically,
     * from the session, into your form. Even better, if you are using "th:action" for your form,
     * Thymeleaf will automatically inject that hidden field for you, without having to do it manually.
     *
     * <form action="/transfer" method="post">  <!-- 1 -->
     * <input type="text" name="amount"/>
     * <input type="text" name="routingNumber"/>
     * <input type="text" name="account"/>
     * <input type="submit" value="Transfer"/>
     * <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
     * </form>
     * <p>
     * <!-- OR -->
     * <p>
     * <form th:action="/transfer" method="post">  <!-- 2 -->
     * <input type="text" name="amount"/>
     * <input type="text" name="routingNumber"/>
     * <input type="text" name="account"/>
     * <input type="submit" value="Transfer"/>
     * </form>
     */

    /**
     * TODO: for db auth this need to be uncommented
     *
     * @param http
     * @throws Exception
     */
//    @Autowired
//    private UserService userService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()// disable csrf or send pass in the token.
                .authorizeRequests().antMatchers("/", "/home").permitAll()
//                .antMatchers("/api/admin").hasRole("ADMIN") // api/admin url should be access only by admin
//                .antMatchers("/delete").hasRole("ADMIN") // api/admin url should be access only by admin
                .anyRequest().authenticated()
                .and()
                .formLogin()
                // custom login page and successful redirect url
//                .loginPage("/login")
//                .defaultSuccessUrl("/tasks")
//                .passwordParameter("custom-password-param") // change the custom login form params names
//                .usernameParameter("custom-username-param")
                .and()
                .logout()
                .logoutUrl("/logout")
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
//                .clearAuthentication(true)
//                .deleteCookies("JSESSIONID", "remember-me")
                .logoutSuccessUrl("/login")
                .permitAll()
                .and()
                .rememberMe() // default to 2 weeks; by default the session expires in 30 mins
//                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(20)) // increase the token validity
//                .rememberMeParameter("custom-remember-me-param") // change default remember me param
                .and()
                .httpBasic();

    }


    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        /**
         * THE PASSWORD SHOULD ALWAYS BE ENCODED
         */
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("pass"))
                .roles("USER")
                .authorities(new SimpleGrantedAuthority("ROLE_USER"))
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("pass"))
                .roles("ADMIN") // roles and authorities should be extracted to a enum
                .authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    /**
     * TODO: for db auth this need to be uncommented and delete the userDetailsService()
     */
/**
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userService);
        return provider;
    }
*/

}

