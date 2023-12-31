package com.justshop.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.justshop")
public class DeliveryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliveryServiceApplication.class, args);
    }

}
