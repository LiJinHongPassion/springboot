package com.example.li.springboot_mail_demo.controller;

import com.example.li.springboot_mail_demo.service.MailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author LJH
 * @date 2019/8/24-21:42
 * @QQ 1755497577
 */
@RestController
@RequestMapping("mail")
public class MailController {
    @Resource
    private MailService mailService;

    /**
     * 描述: 发送邮件
     *
     * 测试：
     *
     * @author LJH-1755497577 2019/8/24 21:46
     * @param to 发给谁
     * @return java.lang.String
     */
    @PostMapping("sendMail")
    public String sendMail(@RequestParam("to") String to){
        mailService.sendSimpleMail(to,"这是标题-codeant","这是内容-codeant");
        return "已发送，请查看";
    }

}
