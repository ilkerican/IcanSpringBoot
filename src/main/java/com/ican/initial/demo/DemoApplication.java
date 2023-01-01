package com.ican.initial.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "Rest API Documentation", version = "1.0", description = "Spring Boot Rest API by ICAN"))

/*
 * 
 * SWAGGER URL : http://localhost:8080/swagger-ui/index.html
 */

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
