package com.example.li.springboot_websocket_simplechat_demo.controller;

import com.example.li.springboot_websocket_simplechat_demo.entity.Greeting;
import com.example.li.springboot_websocket_simplechat_demo.entity.HelloMessage;
import com.example.li.springboot_websocket_simplechat_demo.entity.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

/**
 * @author Li
 * @date 2019/5/19-0:39
 */
@Controller
public class GroupChatController {


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
     public Greeting login(HelloMessage message) throws InterruptedException {
        Thread.sleep(1000);
        return new Greeting(HtmlUtils.htmlEscape(message.getName()));
     }

    @MessageMapping("/groupchat")
    @SendTo("/topic/groupchat")
    public Message groupchat(Message message) throws InterruptedException {
        Thread.sleep(1000);
        return new Message(HtmlUtils.htmlEscape(message.getName()) , HtmlUtils.htmlEscape(message.getInner()));
    }

}
