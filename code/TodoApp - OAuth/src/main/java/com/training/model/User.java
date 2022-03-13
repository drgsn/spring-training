package com.training.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.training.dto.UserDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @JsonIgnore
    private String password;

    private String roles;

    private String permissions;

    public static User from(UserDTO dto){
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRoles(List.of("user"));
        user.setPermissions(List.of("read"));
        return user;
    }

    public static User from(UserDTO user, PasswordEncoder bcryptEncoder) {
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        return from(user);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return Arrays.asList(roles.split(","));
    }

    public void setRoles(List<String> roles) {
        this.roles = StringUtils.join(roles, ",");
    }

    public List<String> getPermissions() {
        return Arrays.asList(permissions.split(","));
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = StringUtils.join(permissions, ",");
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = getRoles().stream().map(r-> new SimpleGrantedAuthority(r)).collect(Collectors.toList());
        authorities.addAll(getPermissions().stream().map(p -> new SimpleGrantedAuthority(p)).collect(Collectors.toList()));
        return authorities;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", permissions=" + permissions +
                '}';
    }
}
