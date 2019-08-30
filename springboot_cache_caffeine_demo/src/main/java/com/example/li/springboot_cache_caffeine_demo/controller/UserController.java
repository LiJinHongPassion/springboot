package com.example.li.springboot_cache_caffeine_demo.controller;

import com.example.li.springboot_cache_caffeine_demo.entity.User;
import com.example.li.springboot_cache_caffeine_demo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author LJH
 * @date 2019/8/30-1:29
 * @QQ 1755497577
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("saveOrUpdate")
    public String saveOrUpdate(){
        userService.saveOrUpdate(new User(4L, "user4"));
        return "ok";
    }

    @GetMapping("get")
    public User get(Long id){
        return userService.get(id);
    }

    @GetMapping("delete")
    public String delete(Long id){
        userService.delete(id);
        return "d-ok";
    }

}
