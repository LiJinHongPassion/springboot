package com.example.li.springboot_junit_demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.li.springboot_junit_demo.dao.base")//将项目中对应的mapper类的路径加进来就可以了
public class SpringbootJunitDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJunitDemoApplication.class, args);
    }

}
