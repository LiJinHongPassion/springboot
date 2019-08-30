package com.example.li.springboot_cache_caffeine_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootCacheCaffeineDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootCacheCaffeineDemoApplication.class, args);
        System.out.println("http://localhost:8080/saveOrUpdate");
        System.out.println("http://localhost:8080/get?id=1");
        System.out.println("http://localhost:8080/delete?id=1");
    }

}
