package com.unieuro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * Aplicação Spring Boot para fornecer API REST para o dashboard web.
 */
@SpringBootApplication
@CrossOrigin(origins = "*")
public class WebApiApplication {
    
    public static void main(String[] args) {
        System.out.println("=== Iniciando API Web para Dashboard de Saúde Mental ===");
        SpringApplication.run(WebApiApplication.class, args);
    }
}

