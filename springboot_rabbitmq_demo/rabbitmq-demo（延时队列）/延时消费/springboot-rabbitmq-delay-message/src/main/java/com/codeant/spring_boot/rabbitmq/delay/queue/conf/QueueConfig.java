package com.codeant.spring_boot.rabbitmq.delay.queue.conf;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * 描述: 创建queue、exchange、绑定路由规则
 *
 * @author LJH-1755497577 2019/10/16 10:57
 */
@Configuration
public class QueueConfig {

    /**
     * 发送到该队列的message会在一段时间后过期进入到delay_process_queue
     * 每个message可以控制自己的失效时间
     */
    public final static String DELAY_QUEUE_PER_MESSAGE_TTL_NAME = "delay_queue_per_message_ttl";

    /**
     * message失效后进入的队列，也就是实际的消费队列
     */
    public final static String DELAY_PROCESS_QUEUE_NAME = "delay_process_queue";



    /**
     * DLX
     */
    public final static String DELAY_EXCHANGE_NAME = "delay_exchange";

    /**
     * 路由到delay_queue_per_message_ttl的exchange
     */
    public final static String PER_MESSAGE_TTL_EXCHANGE_NAME = "per_message_ttl_exchange";



    /**
     * 创建DLX exchange
     *
     * @return
     */
    @Bean
    DirectExchange delayExchange() {
        return new DirectExchange(DELAY_EXCHANGE_NAME);
    }

    /**
     * 创建per_message_ttl_exchange
     *
     * @return
     */
    @Bean
    DirectExchange perMessageTTLExchange() {
        return new DirectExchange(PER_MESSAGE_TTL_EXCHANGE_NAME);
    }



    /**
     * 创建delay_queue_per_message_ttl队列
     *
     * @return
     */
    @Bean
    Queue delayQueuePerMessageTTL() {
        return QueueBuilder.durable(DELAY_QUEUE_PER_MESSAGE_TTL_NAME)
                           .withArgument("x-dead-letter-exchange", DELAY_EXCHANGE_NAME) // DLX，dead letter发送到的exchange
                           .withArgument("x-dead-letter-routing-key", DELAY_PROCESS_QUEUE_NAME) // dead letter携带的routing key
                           .build();
    }

    /**
     * 创建delay_process_queue队列，也就是实际消费队列
     *
     * @return
     */
    @Bean
    Queue delayProcessQueue() {
        return QueueBuilder.durable(DELAY_PROCESS_QUEUE_NAME)
                           .build();
    }



    /**
     * 将DLX绑定到实际消费队列
     *
     * @param delayProcessQueue
     * @param delayExchange
     * @return
     */
    @Bean
    Binding dlxBinding(Queue delayProcessQueue, DirectExchange delayExchange) {
        return BindingBuilder.bind(delayProcessQueue)
                             .to(delayExchange)
                             .with(DELAY_PROCESS_QUEUE_NAME);
    }

    /**
     * 将per_message_ttl_exchange绑定到delay_queue_per_message_ttl队列
     *
     * @param delayQueuePerMessageTTL
     * @param perMessageTTLExchange
     * @return
     */
    @Bean
    Binding messageTTLBinding(Queue delayQueuePerMessageTTL, DirectExchange perMessageTTLExchange) {
        return BindingBuilder.bind(delayQueuePerMessageTTL)
                .to(perMessageTTLExchange)
                .with(DELAY_QUEUE_PER_MESSAGE_TTL_NAME);
    }

}
