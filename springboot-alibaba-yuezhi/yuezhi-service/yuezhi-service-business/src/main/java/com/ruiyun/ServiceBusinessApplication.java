package com.ruiyun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 描述: 业务服务启动类
 *
 * @author lijinhong
 * @date 21.1.28
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ServiceBusinessApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceBusinessApplication.class, args);
    }

}
