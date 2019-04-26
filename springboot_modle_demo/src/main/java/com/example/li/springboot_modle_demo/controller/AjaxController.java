package com.example.li.springboot_modle_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * @author Li
 * @date 2019/4/26-12:57
 */
@Controller
public class AjaxController {

    /***
     *
     * 利用ajax传递html，实现局部刷新
     * @param model
     * @return
     */
    @RequestMapping(value = "/hello3.html")
    public String Hello3(Model model){
        model.addAttribute("inner", "这是内容" + "  时间：" + new Date());
        return "insert1";
    }

}
