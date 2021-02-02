package com.ruiyun.service.Impl;

import com.ruiyun.service.IDiscoveryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述: 服务实现
 *
 * @author lijinhong
 * @date 2021年01月29日 08:36
 */
@RestController
public class DiscoveryService implements IDiscoveryService {

    @GetMapping(value = "/echo/{string}")
//    @SentinelResource("yuezhi-service-business--echo")//echo为引用资源名称
    public String echo(@PathVariable String string) {
        return "Hello Nacos Discovery " + string;
    }
}
