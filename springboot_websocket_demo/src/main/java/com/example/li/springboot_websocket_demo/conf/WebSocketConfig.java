package com.example.li.springboot_websocket_demo.conf;

/**
 * @author Li
 * @date 2019/5/17-13:23
 */
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    //设置消息代理；页面上用js来订阅的地址，也是我们服务器往WebSocket端接收js端发送消息的地址
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        //表明在topic、queue、users这三个域上可以向客户端发消息。
        //config.enableSimpleBroker("/topic","/queue","/users");
        //客户端向服务端发起请求时，需要以/app为前缀。
        //config.setApplicationDestinationPrefixes("/app");
        //给指定用户发送一对一的消息前缀是/users/。
        //config.setUserDestinationPrefix("/users/");

        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //注册消息连接点，为/gs-guide-websocket路径启用SockJS功能
        registry.addEndpoint("/gs-guide-websocket").withSockJS();
    }

}