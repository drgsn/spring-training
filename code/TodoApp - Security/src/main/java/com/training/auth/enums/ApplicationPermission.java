package com.training.auth.enums;

public enum ApplicationPermission {

    READ("read"),
    WRITE("write");

    private final String permission;

    ApplicationPermission(String permission) {
        this.permission = permission;
    }
}
