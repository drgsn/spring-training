package com.training.auth.enums;

import java.util.Set;

import static com.training.auth.enums.ApplicationPermission.READ;
import static com.training.auth.enums.ApplicationPermission.WRITE;

public enum ApplicationRole {

    USER(Set.of(READ)),
    ADMIN(Set.of(READ, WRITE));

    private final Set<ApplicationPermission> permissions;

    ApplicationRole(Set<ApplicationPermission> permissions) {
        this.permissions = permissions;
    }
}
