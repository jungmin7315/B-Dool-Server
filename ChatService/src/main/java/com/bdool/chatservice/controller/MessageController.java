package com.bdool.chatservice.controller;

import com.bdool.chatservice.model.domain.MessageModel;
import com.bdool.chatservice.model.entity.MessageEntity;
import com.bdool.chatservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Controller
@CrossOrigin
@RequestMapping("/api/messages")
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
    }
//
//    @PostMapping("/{channelId}")
//    public ResponseEntity<MessageEntity> sendMessage(
//            @PathVariable UUID channelId,
//            @RequestParam("content") String content,
//            @RequestParam("profileId") UUID profileId,
//            @RequestParam("isEdited") boolean isEdited,
//            @RequestParam("isDeleted") boolean isDeleted,
//            @RequestParam(value = "file", required = false) MultipartFile file) {
//
//        // 파일 처리 및 메시지 저장 로직 추가
//        MessageModel messageModel = new MessageModel(content, profileId, isEdited, isDeleted, file);
//        MessageEntity savedMessage = messageService.save(messageModel);
//        return ResponseEntity.ok(savedMessage);
//    }


    @GetMapping("/{channelId}")
    public ResponseEntity<List<MessageEntity>> findAllChannelId(
            @PathVariable UUID channelId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size) {
        return ResponseEntity.ok(messageService.findByChannelId(channelId, page, size));
    }


    // 메시지 업데이트
    @PutMapping("/{messageId}")
    public ResponseEntity<MessageEntity> update(@PathVariable UUID messageId, @RequestBody MessageModel message) {
        MessageEntity updatedMessage = messageService.update(messageId, message);
        return ResponseEntity.ok(updatedMessage);
    }

    // 모든 메시지 찾기
    @GetMapping("")
    public ResponseEntity<List<MessageEntity>> findAll() {
        List<MessageEntity> messages = messageService.findAll();
        return ResponseEntity.ok(messages);
    }

    // ID로 메시지 찾기
    @GetMapping("/find/{messageId}")
    public ResponseEntity<MessageEntity> findById(@PathVariable UUID messageId) {
        MessageEntity message = messageService.findById(messageId);
        return ResponseEntity.ok(message);
    }

    // ID로 메시지가 존재하는지 확인
    @GetMapping("/exists/{messageId}")
    public ResponseEntity<Boolean> existsById(@PathVariable UUID messageId) {
        boolean exists = messageService.existsById(messageId);
        return ResponseEntity.ok(exists);
    }

    // ID로 메시지 삭제
    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID messageId) {
        messageService.deleteById(messageId);
        return ResponseEntity.noContent().build();  // 삭제 후 204 응답
    }
}
