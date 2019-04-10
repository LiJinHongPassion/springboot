package com.example.li.springboot_dubbo_2_orderservice_consumer_demo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.li.springboot_dubbo_0_interface_demo.bean.UserAddress;
import com.example.li.springboot_dubbo_0_interface_demo.service.OrderService;
import com.example.li.springboot_dubbo_0_interface_demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Li
 * @date 2019/4/10-17:45
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Reference
    UserService userService;


    @Override
    public List<UserAddress> initOrder(String s) {
        System.out.println("用户ID：" + s);
        return userService.getUserAddressList(s);
    }
}
