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
     * 描述: 延时消费 -- 队列自带过期时间
     *
     * @author LJH-1755497577 2019/10/16 12:26
     * @param
     * @return void
     */
    @Test
    public void testDelayQueuePerQueueTTL() throws InterruptedException {
        ProcessReceiver.latch = new CountDownLatch(3);
        for (int i = 1; i <= 3; i++) {
            rabbitTemplate.convertAndSend(QueueConfig.PER_QUEUE_TTL_EXCHANGE_NAME,
                    QueueConfig.DELAY_QUEUE_PER_QUEUE_TTL_NAME,
                    "Message From delay_queue_per_queue_ttl with expiration " + QueueConfig.QUEUE_EXPIRATION);
        }
        ProcessReceiver.latch.await();
    }
}
