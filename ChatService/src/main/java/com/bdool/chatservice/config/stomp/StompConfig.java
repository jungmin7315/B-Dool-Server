package com.bdool.chatservice.config.stomp;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class StompConfig implements WebSocketMessageBrokerConfigurer {

    private final StompHandler stompHandler;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("/ws/chat")
                .setAllowedOriginPatterns("*")
        // client가 sockjs로 개발되어 있을 때만 필요, client가 java면 필요없음
                .withSockJS()
        ;
    }
    // 메시지 브로커를 구성하는 메서드
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/queue","/topic");
        registry.setApplicationDestinationPrefixes("/publish");
    }

    // 클라이언트 인바운드 채널을 구성하는 메서드
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        // stompHandler를 인터셉터로 등록하여 STOMP 메시지 핸들링을 수행
//        registration.interceptors(stompHandler);
//    }
//
//    // STOMP에서 64KB 이상의 데이터 전송을 못하는 문제 해결
//    @Override
//    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
//        registry.setMessageSizeLimit(160 * 64 * 1024);
//        registry.setSendTimeLimit(100 * 10000);
//        registry.setSendBufferSizeLimit(3 * 512 * 1024);
//    }

}
