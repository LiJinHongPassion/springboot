package com.example.li.springboot_filter_interceptor_demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Li
 * @date 2019/4/5-16:42
 */
@Controller
@RequestMapping("/user")
public class UserController
{
    @GetMapping(value = "/login", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String login(@RequestParam("user_id") String id, @RequestParam("user_password") String pwd){
        if( id.equals("") || pwd.equals("") ){
            return "fail";
        }
        return "success";
    }


    @RequestMapping("/testipinterceptor")
    public String URLtest() {
        return "index";
    }
}
