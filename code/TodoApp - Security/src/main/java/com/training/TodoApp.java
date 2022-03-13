package com.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoApp {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TodoApp.class);
		app.run(args);
	}
}
