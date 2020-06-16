package com.example.springbootpaydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootPayDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootPayDemoApplication.class, args);
        System.out.println("访问支付接口:http://127.0.0.1:8080/aliyun/alipay");
    }

}
