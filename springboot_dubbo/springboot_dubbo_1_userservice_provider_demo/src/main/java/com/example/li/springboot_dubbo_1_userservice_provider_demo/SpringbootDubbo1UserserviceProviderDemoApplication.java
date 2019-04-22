package com.example.li.springboot_dubbo_1_userservice_provider_demo;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@EnableDubbo//开启基于注解的dubbo
@SpringBootApplication
@EnableHystrix
public class SpringbootDubbo1UserserviceProviderDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDubbo1UserserviceProviderDemoApplication.class, args);
    }

}
