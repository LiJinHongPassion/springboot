package com.ruiyun.feign;

import com.ruiyun.service.IDiscoveryService;

/**
 * 描述: 服务消费类
 *
 * @author lijinhong
 * @date 2021年01月29日 08:36
 */
public class ServiceFallback implements IDiscoveryService {

    @Override
    public String echo(String str) {
        return "echo fallback";
    }
}