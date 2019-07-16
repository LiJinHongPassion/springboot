package com.example.li.springboot_jjwt_demo.controller;

import com.example.li.springboot_jjwt_demo.util.JwtService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LJH
 * @date 2019/7/16-6:16
 * @QQ 1755497577
 */
@RestController
public class LoginController {

    //需要token验证才能访问
    @RequestMapping("user/hello")
    public String user(){

        return   "hello";
    }


    //获取token
    @RequestMapping("user/test")
    public String test(){

        Map<String, Object> map = new HashMap<>();
        map.put("name", "codeAnt");
        map.put("age", 21);

        return JwtService.createPersonToken(map, "codeAnt");
    }
}