package com.example.springbootgitcommitiddemo;

import org.apache.juli.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:git.properties")
public class SpringbootGitCommitIdDemoApplication {

    LogFactory logFactory = LogFactory.getFactory();
    public static void main(String[] args) {
        SpringApplication.run(SpringbootGitCommitIdDemoApplication.class, args);

    }

}
