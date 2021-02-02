package com.ruiyun.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.ruiyun.service.IDiscoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 描述: 注册服务测试
 *
 * @author lijinhong
 * @date 2021年01月29日 08:36
 */
@RestController
public class DiscoveryController {

    @Autowired
    private IDiscoveryService discoveryService;

    @RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
    @SentinelResource("yuezhi-api-manager--echo")//echo为引用资源名称
    public String echo(@PathVariable String str) {
        return discoveryService.echo(str);
    }
}
