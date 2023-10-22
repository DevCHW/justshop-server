package com.justshop.category;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.justshop")
public class CategoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CategoryServiceApplication.class, args);
    }

}
