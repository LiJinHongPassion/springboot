package com.example.li.springboot_mybatis_shiro_demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.li.springboot_mybatis_shiro_demo.dao.base")//将项目中对应的mapper类的路径加进来就可以了
public class SpringbootMybatisShiroDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMybatisShiroDemoApplication.class, args);
    }

}
