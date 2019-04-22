package com.example.li.springboot_dubbo_2_orderservice_consumer_demo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.li.springboot_dubbo_0_interface_demo.bean.UserAddress;
import com.example.li.springboot_dubbo_0_interface_demo.service.OrderService;
import com.example.li.springboot_dubbo_0_interface_demo.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Li
 * @date 2019/4/10-17:45
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Reference//(url = "127.0.0.1:20882")dubbo直连
    UserService userService;


    @HystrixCommand(fallbackMethod = "reliable")//当initOrder方法执行过程中遇见异常后，就回调reliable方法
    @Override
    public List<UserAddress> initOrder(String s) {
        System.out.println("用户ID：" + s);
        //模拟出错
        if(Math.random() > 0.5){
            throw new RuntimeException();
        }
        return userService.getUserAddressList(s);
    }

    public String reliable(){
        return "helloword";
    }
}
