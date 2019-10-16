package com.codeant.spring_boot.rabbitmq.delay.queue;

import com.codeant.spring_boot.rabbitmq.delay.queue.conf.QueueConfig;
import com.codeant.spring_boot.rabbitmq.delay.queue.service.ProcessReceiver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

/**
 * 描述: 模拟发送消息
 *
 * @author LJH-1755497577 2019/10/16 11:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 描述: 延时重试 -- 消息处理中抛出异常，将消息转发进入
     *
     * @author LJH-1755497577 2019/10/16 12:26
     * @param
     * @return void
     */
    @Test
    public void testFailMessage() throws InterruptedException {
        ProcessReceiver.latch = new CountDownLatch(6);
        for (int i = 1; i <= 3; i++) {
            rabbitTemplate.convertAndSend(
                    QueueConfig.DELAY_EXCHANGE_NAME,
                    QueueConfig.DELAY_PROCESS_QUEUE_NAME,
                    ProcessReceiver.FAIL_MESSAGE);
        }
        ProcessReceiver.latch.await();
    }
}
