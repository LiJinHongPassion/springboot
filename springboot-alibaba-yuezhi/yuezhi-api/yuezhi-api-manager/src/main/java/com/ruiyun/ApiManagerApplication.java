package com.ruiyun;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 描述: 项目启动类
 *
 * @author lijinhong
 * @date 2021年01月29日 08:36
 */
@Slf4j
@SpringBootApplication
@EnableDiscoveryClient//开启服务注册
@EnableFeignClients    //开启fegin
public class ApiManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiManagerApplication.class, args);
        log.info("\n\n\n");
        log.info("/******************测试服务发现、服务消费*********************/");
        log.info("1. 启动服务提供者 yuezhi-service-business");
        log.info("2. 启动服务消费者 yuezhi-api-manager");
        log.info("3. 访问 http://127.0.0.1:8081/echo/helloword");

        log.info("\n\n\n");
        log.info("/******************测试配置更新*********************/");

        log.info("1. nacos中创建 ${prefix}-${spring.profiles.active}.${file-extension}的配置，这里是yuezhi-api-manager-dev.yml ， 提交useLocalCache: false配置到nacos");
        log.info("2. GET访问 http://127.0.0.1:8081/config/get  查看返回值");
        log.info("3. nacos中更新yuezhi-api-manager-dev.yml中 useLocalCache: true 配置到nacos");
        log.info("4. GET访问 http://127.0.0.1:8081/config/get  查看更新后的返回值");
        log.info("\n\n\n");

    }

}
