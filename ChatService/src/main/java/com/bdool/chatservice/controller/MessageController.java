package com.bdool.chatservice.controller;

import com.bdool.chatservice.model.domain.MessageModel;
import com.bdool.chatservice.model.entity.MessageEntity;
import com.bdool.chatservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    // 클라이언트가 "/publish/message"로 메시지를 보낼 때 호출됨
    // STOMP 메시지 처리 부분은 그대로 유지
    @MessageMapping("/message/{channelId}")
    public void sendMessage(@DestinationVariable UUID channelId, MessageModel messageModel) {
        // 받은 메시지를 저장하고 반환
        MessageEntity savedMessage = messageService.save(messageModel);

        // 저장된 메시지를 구독중인 클라이언트에게 전송
        messagingTemplate.convertAndSend("/topic/channel/" + channelId, savedMessage);

//        // 모든 사용자의 안 읽은 메시지 수 계산 후 알림
//        List<Long> allUserIds = messageService.findAllProfileIdsInChannel(channelId);
//        allUserIds.forEach(profileId -> {
//            long unreadCount = messageService.countUnreadMessages(channelId, profileId);
//            messagingTemplate.convertAndSendToUser(profileId.toString(), "/queue/unread-count", unreadCount);
//        });
    }

//    // 메시지 읽음 상태 업데이트
//    @MessageMapping("/message/read/{channelId}")
//    public void updateReadStatus(@DestinationVariable UUID channelId, Principal principal, UUID lastReadMessageId) {
//        Long profileId = Long.parseLong(principal.getName());  // 사용자 ID 추출
//        messageService.updateReadStatus(channelId, profileId, lastReadMessageId);
//
//        // 안 읽은 메시지 수 업데이트 후 전송
//        long unreadCount = messageService.countUnreadMessages(channelId, profileId);
//        messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/unread-count", unreadCount);
//    }
}
