package com.example.li.springboot_fastdfs_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author LJH
 * @date 2019/7/25-17:06
 * @QQ 1755497577
 */
@Controller
public class PageController {

    @RequestMapping("/fileupload.html")
    public String fileupload(){
        return "fileupload";
    }
}
