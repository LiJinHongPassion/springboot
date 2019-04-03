package com.example.li.springboot_mybatis_demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.example.li.springboot_mybatis_demo.controller", "com.example.li.springboot_mybatis_demo.service", "com.example.li.springboot_mybatis_demo.dao.base" })//自动扫扫描带spring注解路径
@MapperScan("com.example.li.springboot_mybatis_demo.dao.base")//将项目中对应的mapper类的路径加进来就可以了
public class SpringbootMybatisDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMybatisDemoApplication.class, args);
    }

}
