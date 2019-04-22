package com.example.li.springboot_dubbo_1_userservice_provider_demo.service.impl;

import com.example.li.springboot_dubbo_0_interface_demo.bean.UserAddress;
import com.example.li.springboot_dubbo_0_interface_demo.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author Li
 * @date 2019/4/10-17:45
 */
@com.alibaba.dubbo.config.annotation.Service//暴露服务
@Service
public class UserServiceImpl implements UserService {

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000") })
    @Override
    public List<UserAddress> getUserAddressList(String s) {
        System.out.println("UserServiceImpl..3.....");
        UserAddress address1 = new UserAddress(1, "北京市昌平区宏福科技园综合楼3层", "1", "李老师", "010-56253825", "Y");
        UserAddress address2 = new UserAddress(2, "深圳市宝安区西部硅谷大厦B座3层（深圳分校）", "1", "王老师", "010-56253825", "N");


        return Arrays.asList(address1,address2);
    }
}
