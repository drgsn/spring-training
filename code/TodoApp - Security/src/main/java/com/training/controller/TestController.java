package com.training.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/api/test")
public class TestController {


    @GetMapping("/{id}")
    public String findById(@PathVariable long id) {
        return "id = " + id;
    }

    @GetMapping("/")
    public Collection<String> findBooks() {
        return new ArrayList<>();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String update(
            @PathVariable("id") final String id) {
        return "an update request has been made";
    }
}