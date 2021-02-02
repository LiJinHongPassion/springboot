package com.ruiyun.feign;

import org.springframework.context.annotation.Bean;

/**
 * 描述: 注册服务测试
 *
 * @author lijinhong
 * @date 2021年01月29日 08:36
 */
public class FeignConfiguration {
    @Bean
    public ServiceFallback echoServiceFallback() {
        return new ServiceFallback();
    }
}