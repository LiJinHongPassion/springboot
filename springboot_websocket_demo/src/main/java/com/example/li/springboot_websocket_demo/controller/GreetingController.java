package com.example.li.springboot_websocket_demo.controller;

/**
 * @author Li
 * @date 2019/5/17-13:22
 */
import com.example.li.springboot_websocket_demo.entity.Greeting;
import com.example.li.springboot_websocket_demo.entity.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    //@MessageMapping注解，GreetingController（）方法能够处理指定目的地上到达的消息。本例中目的地也就是“/app/hello”。（“/app”前缀是隐含 的，因为我们将其配置为应用的目的地前缀）
    //@SubscribeMapping注解，与@MessageMapping注解相似，当收到了STOMP订阅消息的时候，带有@SubscribeMapping注解的方法将会被触发。
    @MessageMapping("/hello")

    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
}