package com.example.li.springboot_dubbo_2_orderservice_consumer_demo.controller;

import com.example.li.springboot_dubbo_0_interface_demo.bean.UserAddress;
import com.example.li.springboot_dubbo_0_interface_demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Li
 * @date 2019/4/10-17:47
 */
@Controller
public class OrderController {
    @Autowired
    OrderService orderService;

    @ResponseBody
    @RequestMapping("/initOrder")
    public List<UserAddress> initOrder(@RequestParam("uid")String userId) {
        return orderService.initOrder(userId);
    }


}
