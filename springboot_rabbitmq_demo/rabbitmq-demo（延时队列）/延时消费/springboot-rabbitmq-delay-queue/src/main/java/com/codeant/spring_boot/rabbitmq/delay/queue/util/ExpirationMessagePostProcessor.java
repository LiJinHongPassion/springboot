package com.codeant.spring_boot.rabbitmq.delay.queue.util;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;


/**
 * 描述: 设置队列消息的过期时间
 *
 * @author LJH-1755497577 2019/10/16 11:00
 */
public class ExpirationMessagePostProcessor implements MessagePostProcessor {
    // 毫秒
    private final Long ttl;

    public ExpirationMessagePostProcessor(Long ttl) {
        this.ttl = ttl;
    }

    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        message.getMessageProperties()
                .setExpiration(ttl.toString()); // 设置per-message的失效时间
        return message;
    }
}
