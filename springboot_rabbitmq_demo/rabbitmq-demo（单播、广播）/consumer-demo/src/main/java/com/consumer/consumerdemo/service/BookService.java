package com.consumer.consumerdemo.service;

import com.consumer.consumerdemo.bean.Book;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    /**
     * 描述: 加了监听器会自动消费，而不需要方法调用消费
     *
     */
    @RabbitListener(queues = "codeant.queue")
    public void receive(Book book){
        System.out.println("收到消息：" + book.toString());
    }

    @RabbitListener(queues = "codeantnew.queue")
    public void receive02(Book book){
        System.out.println("收到消息：" + book.toString());
    }
}
