//package com.bdool.chatservice.config.stomp;
//
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.support.ChannelInterceptor;
//import org.springframework.stereotype.Component;
//
//@Component
//public class StompHandler implements ChannelInterceptor {
//
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        // 메시지 유효성 검사, 인증 처리 등 인터셉트 로직 추가
//        System.out.println("STOMP 메시지 인터셉터: 메시지가 전송되기 전에 처리됩니다.");
//        return message;
//    }
//
//    @Override
//    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
//        // 메시지 전송 후 처리 로직 추가 가능
//        System.out.println("STOMP 메시지 인터셉터: 메시지 전송 후 처리됩니다.");
//    }
//
//    // 기타 ChannelInterceptor 메서드 구현 가능 (afterSendCompletion, preReceive 등)
//}
