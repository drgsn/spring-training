package com.training.beans;

import org.springframework.stereotype.Component;

@Component
public class Organization {

    private String name;

    public Organization() {
        this.name = "Test name 1";
    }

    public Organization(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
