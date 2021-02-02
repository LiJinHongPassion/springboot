package com.ruiyun.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 描述: 自动配置测试
 *
 * @author lijinhong
 * @date 2021年01月29日 08:36
 */
@RestController
@RequestMapping("/config")
@RefreshScope// 实现配置自动更新
public class ConfigController {

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;

    @RequestMapping("/get")
    public boolean get() {
        return useLocalCache;
    }
}