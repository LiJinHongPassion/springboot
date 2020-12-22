package com.example.codeant.springbootsyncdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SpringbootSyncDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSyncDemoApplication.class, args);
    }

}
