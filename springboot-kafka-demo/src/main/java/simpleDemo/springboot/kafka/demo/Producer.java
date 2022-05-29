package simpleDemo.springboot.kafka.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author gp
 * @Date 2022/5/27
 * 😁😄
 **/
@Component
public class Producer {
    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;
    public void send(String msg){
        kafkaTemplate.send("test",msg);
    }
}
