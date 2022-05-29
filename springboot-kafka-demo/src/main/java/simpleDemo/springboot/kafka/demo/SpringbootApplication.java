package simpleDemo.springboot.kafka.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gp
 * @Date 2022/5/24
 * 😁😄
 **/
@SpringBootApplication
@RestController
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class,args);
    }
    @Autowired
    Producer producer;
    @GetMapping("send")
    public String send(String msg){
        producer.send(msg);
        return "success";
    }
}
