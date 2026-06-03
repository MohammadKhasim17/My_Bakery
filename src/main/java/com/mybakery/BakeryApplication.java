package com.mybakery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the MyBakery Spring Boot application.
 * {@code @SpringBootApplication} enables auto-configuration, component scanning,
 * and registers this class as a configuration source.
 */
@SpringBootApplication
public class BakeryApplication {

    public static void main(String[] args) {
        SpringApplication.run(BakeryApplication.class, args);
    }
}
