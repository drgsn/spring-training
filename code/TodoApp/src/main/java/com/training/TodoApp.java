package com.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class TodoApp {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TodoApp.class);
		app.run(args);
	}
}
