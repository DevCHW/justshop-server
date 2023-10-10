package com.justshop.point;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "com.justshop")
@EnableDiscoveryClient
public class PointServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PointServiceApplication.class, args);
    }

}
