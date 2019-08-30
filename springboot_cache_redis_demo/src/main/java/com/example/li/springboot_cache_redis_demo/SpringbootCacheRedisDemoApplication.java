package com.example.li.springboot_cache_redis_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootCacheRedisDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootCacheRedisDemoApplication.class, args);
        System.out.println("http://localhost:8080/saveOrUpdate");
        System.out.println("http://localhost:8080/get?id=1");
        System.out.println("http://localhost:8080/delete?id=1");
    }

}
