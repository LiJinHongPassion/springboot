package com.example.li.springboot_mybatis_shiro_demo.controller;

/**
 * @author Li
 * @date 2018/10/14-16:39
 */
import com.alibaba.fastjson.JSON;
import com.example.li.springboot_mybatis_shiro_demo.entity.User;
import com.example.li.springboot_mybatis_shiro_demo.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService userService;


    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam("user_id") String user_id,
                        @RequestParam("password") String password){

        Map<String, Object> result = new HashMap<>();

//        result = userService.getById(user_id);

        Subject currentUser = SecurityUtils.getSubject();

        if (!currentUser.isAuthenticated()) {
            // 把用户名和密码封装为 UsernamePasswordToken 对象
            UsernamePasswordToken token = new UsernamePasswordToken(user_id, password);
            // rememberme
            token.setRememberMe(true);
            try {
                System.out.println("1. " + token.hashCode());
                // 执行登录.realm里面的doGetAuthenticationInfo方法实现密码比对
                currentUser.login(token);

                result = userService.getById(user_id);

                result.put("result","1");

            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            // 所有认证时异常的父类.
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
                System.out.println("登录失败: " + ae.getMessage());

                result.put("result","-1");
            }
        }else{
            result.put("result","1:已登录");
        }

        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping("/register")
    public String register(@RequestParam("user_id") String user_id, @RequestParam("user_name") String user_name,
                           @RequestParam("password") String password){

        Map<String, Object> result = new HashMap<>();

        User user = new User();
        user.setUser_id(user_id);
        user.setUser_password(password);
        user.setUser_name(user_name);
        result = userService.add(user.toMap());

        return JSON.toJSONString(result);
    }

}
