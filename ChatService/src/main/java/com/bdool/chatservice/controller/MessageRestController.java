package com.bdool.chatservice.controller;

import com.bdool.chatservice.model.domain.MessageModel;
import com.bdool.chatservice.model.entity.MessageEntity;
import com.bdool.chatservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageRestController {

    private final MessageService messageService;

    // 특정 채널의 메시지 조회
    @GetMapping("/{channelId}")
    public ResponseEntity<List<MessageEntity>> findAllChannelId(
            @PathVariable UUID channelId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size) {
        return ResponseEntity.ok(messageService.findByChannelId(channelId, page, size));
    }

    // 메시지 수정
    @PutMapping("/{messageId}")
    public ResponseEntity<MessageEntity> update(@PathVariable UUID messageId, @RequestBody MessageModel message) {
        MessageEntity updatedMessage = messageService.update(messageId, message);
        return ResponseEntity.ok(updatedMessage);
    }

    // ID로 메시지 찾기
    @GetMapping("/find/{messageId}")
    public ResponseEntity<MessageEntity> findById(@PathVariable UUID messageId) {
        MessageEntity message = messageService.findById(messageId);
        return ResponseEntity.ok(message);
    }

    // ID로 메시지 삭제
    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID messageId) {
        messageService.deleteById(messageId);
        return ResponseEntity.noContent().build();  // 삭제 후 204 응답
    }
}
