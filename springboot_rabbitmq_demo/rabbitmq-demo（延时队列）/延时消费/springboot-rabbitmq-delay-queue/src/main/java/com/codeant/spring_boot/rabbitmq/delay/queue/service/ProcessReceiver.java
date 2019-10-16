package com.codeant.spring_boot.rabbitmq.delay.queue.service;

import com.codeant.spring_boot.rabbitmq.delay.queue.conf.QueueConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;


/**
 * 描述：接收消息
 *
 * @author LJH-1755497577 2019/10/16 11:00
 */
@Component
public class ProcessReceiver {
    public static CountDownLatch latch;
    private static Logger logger = LoggerFactory.getLogger(ProcessReceiver.class);



    /**
     * 模拟消息处理。如果当消息内容为FAIL_MESSAGE的话，则需要抛出异常
     *
     * @param message
     * @throws Exception
     */
    @RabbitListener(queues = QueueConfig.DELAY_PROCESS_QUEUE_NAME)
    public void processMessage(Message message) {
        String realMessage = new String(message.getBody());
        logger.info("Received <" + realMessage + ">");
    }
}
