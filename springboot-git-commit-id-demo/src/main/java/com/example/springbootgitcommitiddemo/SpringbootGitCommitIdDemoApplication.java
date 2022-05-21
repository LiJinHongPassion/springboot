package com.example.springbootgitcommitiddemo;

import org.apache.juli.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:git.properties") // mvn package后，会生成git.properties，如果没有这个文件，就启动不了
public class SpringbootGitCommitIdDemoApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringbootGitCommitIdDemoApplication.class, args);
        String branch = context.getEnvironment().getProperty("git.branch");
        System.out.println("git.branch:::" + branch);
    }

}
