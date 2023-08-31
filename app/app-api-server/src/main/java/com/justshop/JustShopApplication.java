package com.justshop;

import com.justshop.config.BeanNameConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(nameGenerator = BeanNameConfig.class)
public class JustShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(JustShopApplication.class, args);
    }

}
