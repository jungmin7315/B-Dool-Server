package com.bdool.messageservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")  // 클라이언트가 WebSocket으로 연결할 엔드포인트
                .setAllowedOrigins("*")
                .withSockJS();  // WebSocket을 지원하지 않는 브라우저를 위한 대체 옵션
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");  // 메시지를 브로드캐스트할 경로
        config.setApplicationDestinationPrefixes("/app");  // 클라이언트가 메시지를 보낼 경로
    }
}

