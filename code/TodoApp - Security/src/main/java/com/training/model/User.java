package com.training.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.training.auth.jwt.UserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @JsonIgnore
    private String password;

    @ElementCollection(targetClass = String.class)
    private List<String> roles;

    @ElementCollection(targetClass = String.class)
    private List<String> permissions;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
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
