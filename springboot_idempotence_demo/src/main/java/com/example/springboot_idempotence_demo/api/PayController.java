package com.example.springboot_idempotence_demo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 21.1.11
 */
@RestController
public class PayController {

    @Autowired
    private PayService service;


    @GetMapping("token")
    public String getToken(){
        return service.getToken();
    }

    @PostMapping("pay")
    public String pay(HttpServletRequest request){
        return service.pay(request);
    }
}
