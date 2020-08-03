package com.example.miaoshademo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.miaoshademo.dao")//将项目中对应的mapper类的路径加进来就可以了
public class SpringbootRedisDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRedisDemoApplication.class, args);
        System.out.println("http://localhost:9090/ms/get");
        System.out.println("http://localhost:9090/ms/ready");
    }

}
