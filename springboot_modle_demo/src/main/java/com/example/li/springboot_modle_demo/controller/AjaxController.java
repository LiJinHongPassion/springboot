package com.example.li.springboot_modle_demo.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    /***
     * 利用ajax返回json字符串，在js里面调用字符串
     * @return
     */
    @RequestMapping(value = "/hello4.html")
    @ResponseBody
    public String Hello4(){
        Map<String, Object> map = new HashMap<>();

        map.put("hello", "hello world!");
        map.put("inner", "这是json内容" + "  时间：" + new Date());

        return JSON.toJSONString(map);
    }

}
