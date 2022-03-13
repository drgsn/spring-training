package com.training.auth.jwt;


import com.training.model.User;
import com.training.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        List<GrantedAuthority> permissions = user.getPermissions().stream()
                .map(p -> new SimpleGrantedAuthority(p)).collect(Collectors.toList());

        List<GrantedAuthority> roles = user.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r)).collect(Collectors.toList());

        roles.addAll(permissions);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles);
    }

    //save new user
    public User save(User user) {
        return userRepository.save(user);
    }

    public User save(UserDTO dto) {
        return save(User.from(dto, bcryptEncoder));
    }
}