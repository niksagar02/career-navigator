package com.example.api.in.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Spring Boot application.
 *
 * This class launches the embedded server and initializes
 * all Spring components (controllers, services, etc.).
 */
@SpringBootApplication
public class ApiInWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiInWebApplication.class, args);

        // Simple console log to confirm successful startup
        System.out.println("--------------------------------------------------");
        System.out.println(" Application running at: http://localhost:8080");
        System.out.println("--------------------------------------------------");
    }
}
