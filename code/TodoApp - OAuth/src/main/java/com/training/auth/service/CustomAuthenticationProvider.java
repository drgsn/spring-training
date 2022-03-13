package com.training.auth.service;

import com.training.model.User;
import com.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private UserService userService;


    @Autowired
    private RedisTokenStore redisTokenStore;

    @Autowired
    private RedisLoginService redisLoginService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("Trying to authenticate " + authentication.getName());
        String usernameAttempt = authentication.getName().toLowerCase().trim();
        User user;
        if (redisLoginService.isExpired(usernameAttempt)) {
            user = tryAuthentication(usernameAttempt, authentication.getCredentials().toString());
        } else {
            user = tryAuthentication(usernameAttempt, authentication.getCredentials().toString());
        }

        List<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());
        SimpleGrantedAuthority rolePaymentAdmin = new SimpleGrantedAuthority("ROLE_ADMIN");
        HashMap details = ((HashMap) authentication.getDetails());

        Boolean containsRolePaymentAdmin = user.getAuthorities().contains(rolePaymentAdmin);

        authorities.addAll(user.getAuthorities());


        Authentication newAuth = new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), authorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        HashMap<String, Object> actionDetails = new HashMap<>();
        actionDetails.put("username", usernameAttempt);
        actionDetails.put("authorities", newAuth.getAuthorities());

        return newAuth;
    }

    /**
     * Checks if the provided code can be converted to the Long format.
     *
     * @param code the code attempt
     * @return if the code can be converted
     */
    private boolean isValidLong(String code) {
        try {
            Long.parseLong(code);
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the entered password is the same as the hashed password stored in the database
     * and if the provided username exists in the database.
     *
     * @param candidate specifies the entered password
     * @return the found user if authentication was successful
     */
    private User tryAuthentication(String attemptString, String candidate) {
        User user = userService.loadUserByUsername(attemptString);
        if (user == null) {
            System.out.println("Invalid username/password combination " + attemptString);
            BadCredentialsException exception = new BadCredentialsException("Invalid username/password combination!");
            throw exception;
        }

        if (!BCrypt.checkpw(candidate, user.getPassword())) {
            System.out.println("Invalid username/password combination " + attemptString);
            BadCredentialsException exception = new BadCredentialsException("Invalid username/password combination!");
            throw exception;
        }
        return user;
    }

    /**
     * Removes every token associated with the provided user.
     *
     * @param user the specified user
     */
    private void removeAccessTokens(User user) {
        Collection<OAuth2AccessToken> token_list = redisTokenStore.findTokensByClientIdAndUserName("browser", user.getUsername());
        for (OAuth2AccessToken aToken_list : token_list) {
            if (aToken_list.getAdditionalInformation().get("deviceID") == null) {
                redisTokenStore.removeRefreshToken(aToken_list.getRefreshToken());
                redisTokenStore.removeAccessToken(aToken_list);
            }
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }

}
