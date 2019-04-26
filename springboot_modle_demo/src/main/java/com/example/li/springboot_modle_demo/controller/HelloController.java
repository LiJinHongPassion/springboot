package com.example.li.springboot_modle_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * 该控制器展示的是Model、ModelMap、ModelAndView的简单用法，模板引擎使用的是thymeleaf，
 * 通过“${}”访问Model、ModelMap、ModelAndView中的属性
 * Model、ModelMap、ModelAndView的生命周期在跳转到view就销毁了
 *
 * @author Li
 * @date 2019/4/26-12:07
 */
@Controller
public class HelloController {

    @RequestMapping(value = "/hello.html")
    public String Hello(Model model){
        model.addAttribute("hello", "model--->hello world!");
        return "hello";
    }

    @RequestMapping(value = "/hello1.html")
    public String Hello(ModelMap model){
        model.addAttribute("hello", "ModelMap--->hello world!");
        return "hello";
    }

    @RequestMapping(value = "/hello2.html")
    public ModelAndView Hello(){
        ModelAndView modelAndView = new ModelAndView("hello");
        modelAndView.addObject("hello", "ModelAndView--->hello world!");
        return modelAndView;
    }

}
