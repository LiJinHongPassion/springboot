package com.example.li.springboot_websocket_simplechat_demo.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author Li
 * @date 2019/5/19-0:40
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
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
