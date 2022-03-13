package com.training.auth.basic;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@Service
public class UserService implements UserDetailsService {

    // inject user repository
    // load user from database

//    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // repository.findByUsername(username);
        return null;
    }
}
