package com.example.li.springboot_dubbo_2_orderservice_consumer_demo;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo//开启dubbo注解功能
@SpringBootApplication
public class SpringbootDubbo2OrderserviceConsumerDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDubbo2OrderserviceConsumerDemoApplication.class, args);
    }

}
