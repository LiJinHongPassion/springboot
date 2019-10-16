package com.wilson.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述：所有声明的这些queue，exchange，以及绑定关系会在第一次发送消息时建立在rabbitmq中。
 *
 * @author LJH
 * @date 2019/10/15-20:08
 * @QQ 1755497577
 */
@Configuration
public class RabbitConfig {
//
//
//    @Bean
//    public Exchange codeantDirectExchange() {
//        return ExchangeBuilder
//                .directExchange("codeant.direct")
//                .durable(true)
//                .build();
//    }
//
//
//    @Bean
//    public Exchange codeantnewFanoutExchange() {
//        return ExchangeBuilder
//                .fanoutExchange("codeantnew.fanout")
//                .durable(true)
//                .build();
//    }
//
//
//    @Bean
//    public Queue codeantnewQueue() {
//        return QueueBuilder.durable("codeantnew.queue")
//                .build();
//    }
//
//
//    @Bean
//    public Queue codeantQueue() {
//        return QueueBuilder.durable("codeant.queue")
//                .build();
//    }
//
//    @Bean
//    public Binding codeantnewFanoutExchange2codeantnewQueueBiding() {
//        return BindingBuilder.bind(codeantnewQueue())
//                .to(codeantnewFanoutExchange())
//                .with("codeantnew.haha").noargs();
//    }
//    @Bean
//    public Binding codeantnewFanoutExchange2codeantQueueBiding() {
//        return BindingBuilder.bind(codeantQueue())
//                .to(codeantnewFanoutExchange())
//                .with("codeant.haha").noargs();
//    }
//    @Bean
//    public Binding codeantDirectExchangeExchange2codeantnewQueueBiding() {
//        return BindingBuilder.bind(codeantnewQueue())
//                .to(codeantDirectExchange())
//                .with("codeantnew.haha").noargs();
//    }
//
//    @Bean
//    public Binding codeantDirectExchangeExchange2codeantQueueBiding() {
//        return BindingBuilder.bind(codeantQueue())
//                .to(codeantDirectExchange())
//                .with("codeant.haha").noargs();
//    }

}

