package com.example.springbootexamplehttps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootExampleHttpsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootExampleHttpsApplication.class, args);
        System.out.println("http://127.0.0.1:9999/example/hello");
    }

}
