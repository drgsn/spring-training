package com.training.service;

import com.training.beans.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class OrganizationService {

    @Autowired
    private Organization organization;

    @PostConstruct
    public void init() {
        System.out.println("name from organization service" + organization.getName());
    }
}
