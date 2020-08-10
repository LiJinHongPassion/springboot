package com.example.miaoshademo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootRedisDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRedisDemoApplication.class, args);
        System.out.println("http://localhost:9090/jz/pay?id=1");
        System.out.println("该示例能够正常加锁, 但解锁功能有问题, 无法解锁");
    }

}
