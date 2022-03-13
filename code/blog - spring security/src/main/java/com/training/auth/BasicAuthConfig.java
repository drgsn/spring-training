package com.training.auth;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class BasicAuthConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // configure which urls the user has access to.
        http
                .csrf().disable()// disable csrf or send pass in the token.
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/delete").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .logout()
                .permitAll()
                .and()
                .rememberMe() // default to 2 weeks; by default the session expires in 30 mins
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

}
