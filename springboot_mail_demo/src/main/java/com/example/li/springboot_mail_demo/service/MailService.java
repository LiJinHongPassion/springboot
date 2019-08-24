package com.example.li.springboot_mail_demo.service;

/**
 * 描述: 邮件服务接口类
 *
 * @author LJH-1755497577 2019/8/24 21:39
 */
public interface MailService {
    /**
     * 描述: 发送邮件
     *
     * @author LJH-1755497577 2019/8/24 21:40
     * @param to 发送给谁
     * @param subject 标题
     * @param content 内容
     * @return void
     */
    void sendSimpleMail(String to, String subject, String content);
}
