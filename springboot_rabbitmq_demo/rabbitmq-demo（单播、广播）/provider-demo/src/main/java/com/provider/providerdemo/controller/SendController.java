package com.provider.providerdemo.controller;

import com.provider.providerdemo.bean.Book;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述：生产者
 *
 * @author LJH
 * @date 2019/10/15-15:34
 * @QQ 1755497577
 */
@RestController
public class SendController {


    @Autowired
    RabbitTemplate rabbitTemplate;


    /**
     * 描述: 单播
     *
     * 效果：在没消费者消费之前，只有路由key为codeant.haha会增加一条消息
     *
     * @author LJH-1755497577 2019/10/15 16:09
     * @param
     * @return java.lang.String
     */
    @GetMapping("sendMsgD")
    public String sendMsgD(){
        //Message需要自己构造一个;定义消息体内容和消息头
        //rabbitTemplate.send(exchage,routeKey,message);

        //object默认当成消息体，只需要传入要发送的对象，自动序列化发送给rabbitmq；
        //rabbitTemplate.convertAndSend(exchage,routeKey,object);
        Map<String,Object> map = new HashMap<>();
        map.put("msg","这是第一个消息");
        map.put("data", Arrays.asList("helloworld",123,true));
        //对象被默认序列化以后发送出去
        rabbitTemplate.convertAndSend("codeant.direct","codeant.haha",new Book("西游记","吴承恩"));
        return "succ";
     }



    /**
     * 描述: 广播
     *
     * 效果：在没消费者消费之前，所有队列都会会增加一条消息，看效果前需要注释掉启动类的@EnableRabbit
     *
     * @author LJH-1755497577 2019/10/15 16:11
     * @param
     * @return java.lang.String
     */
    @GetMapping("sendMsgF")
    public String sendMsgF(){
        //Message需要自己构造一个;定义消息体内容和消息头
        //rabbitTemplate.send(exchage,routeKey,message);

        //object默认当成消息体，只需要传入要发送的对象，自动序列化发送给rabbitmq；
        //rabbitTemplate.convertAndSend(exchage,routeKey,object);
        Map<String,Object> map = new HashMap<>();
        map.put("msg","这是第二个消息");
        map.put("data", Arrays.asList("helloworld2",123,true));
        //对象被默认序列化以后发送出去
        rabbitTemplate.convertAndSend("codeantnew.fanout",""/*广播不需要路由key*/,/*map*/new Book("红楼","曹雪芹"));
        return "succ";
    }
}
