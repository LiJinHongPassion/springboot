package com.example.springbootteststarterdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.example.mystarterdemo.config"})
public class SpringbootTestStarterDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootTestStarterDemoApplication.class, args);
        while (true){

        }
    }

}
