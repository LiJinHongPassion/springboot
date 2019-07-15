package com.example.li.springboot_swagger_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc//开启MVC
public class SpringbootSwaggerDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSwaggerDemoApplication.class, args);
    }

}
