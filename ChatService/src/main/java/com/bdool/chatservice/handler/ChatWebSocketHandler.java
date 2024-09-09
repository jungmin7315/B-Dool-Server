package com.bdool.chatservice.handler;

import com.bdool.chatservice.model.domain.MessageModel;
import com.bdool.chatservice.model.entity.MessageEntity;
import com.bdool.chatservice.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final MessageService messageService;
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);  // 세션을 제거하여 더 이상 브로드캐스트 대상에서 제외
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        String payload = textMessage.getPayload();

        // 받은 메시지를 MessageModel로 변환
        MessageModel messageModel = objectMapper.readValue(payload, MessageModel.class);

        // 메시지를 저장 (변환은 서비스에서 처리)
        MessageEntity savedMessageEntity = messageService.save(messageModel);

        // 저장된 메시지를 모든 연결된 클라이언트에게 브로드캐스트
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                // 저장된 메시지를 클라이언트에게 JSON 형태로 전송
                webSocketSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(savedMessageEntity)));
            }
        }
    }

}
