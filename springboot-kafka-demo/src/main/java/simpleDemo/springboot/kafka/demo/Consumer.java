package simpleDemo.springboot.kafka.demo;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author gp
 * @Date 2022/5/24
 * 😁😄
 **/
@Component
public class Consumer {


    @KafkaListener(topics = "test",groupId = "test5")
    public void test(ConsumerRecord<?, ?> record){
        Object value = record.value();
        System.out.println(value.toString());

    }

}
