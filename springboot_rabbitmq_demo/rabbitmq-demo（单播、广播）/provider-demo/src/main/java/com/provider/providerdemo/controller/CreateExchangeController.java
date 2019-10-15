package com.provider.providerdemo.controller;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：创建exchange，绑定规则
 *
 * @author LJH
 * @date 2019/10/15-15:37
 * @QQ 1755497577
 */
@RestController
public class CreateExchangeController {

    @Autowired
    AmqpAdmin amqpAdmin;

    @GetMapping("create")
    public String create(){
        createQueue();
        createDirectExchange();
        createFanoutExchange();
        return "succ";
    }

    /**
     *   创建两个exchange：codeant(单播)    codeant.new（广播）
     *
     *   创建两个queue：codeant.queue    codeantnew.queue
     *
     *  1. 创建exchange
     *  2. 创建queue
     *  3. 绑定exchange和queue
     */

    public void createQueue(){
        /*durable：是否持久化*/
        amqpAdmin.declareQueue(new Queue("codeant.queue",true));
        amqpAdmin.declareQueue(new Queue("codeantnew.queue",true));

        //删除queue
//        amqpAdmin.deleteQueue();
    }


    /*单播*/
    public void createDirectExchange(){

        //创建声明exchange，这里是DirectExchange类型
		amqpAdmin.declareExchange(new DirectExchange("codeant.direct"));
		System.out.println("codeant.direct - DirectExchange - 创建完成");

        //创建绑定规则
        amqpAdmin.declareBinding(new Binding("codeantnew.queue",
                Binding.DestinationType.QUEUE,
                "codeant.direct",
                "codeantnew.haha",
                null)
        );

        amqpAdmin.declareBinding(new Binding("codeant.queue",
                Binding.DestinationType.QUEUE,
                "codeant.direct",
                "codeant.haha",
                null)
        );

        //删除exchange
//        amqpAdmin.deleteExchange();
    }

    /*广播*/
    public void createFanoutExchange(){

        //创建声明exchange，这里是DirectExchange类型
        amqpAdmin.declareExchange(new FanoutExchange("codeantnew.fanout"));
        System.out.println("codeant.fanout - fanoutExchange - 创建完成");

        //创建绑定规则
        amqpAdmin.declareBinding(new Binding("codeantnew.queue",
                Binding.DestinationType.QUEUE,
                "codeantnew.fanout",
                "codeantnew.haha",
                null)
        );

        amqpAdmin.declareBinding(new Binding("codeant.queue",
                Binding.DestinationType.QUEUE,
                "codeantnew.fanout",
                "codeant.haha",
                null)
        );

        //删除exchange
//        amqpAdmin.deleteExchange();
    }


}
