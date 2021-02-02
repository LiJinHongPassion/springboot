package com.ruiyun.service;

import com.ruiyun.feign.FeignConfiguration;
import com.ruiyun.feign.ServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 描述: 服务消费类
 *
 * @author lijinhong
 * @date 2021年01月29日 08:36
 */
@FeignClient(name = "yuezhi-service-business", fallback = ServiceFallback.class, configuration = FeignConfiguration.class)
public interface IDiscoveryService {
    @GetMapping(value = "/echo/{str}")
    String echo(@PathVariable("str") String str);
}