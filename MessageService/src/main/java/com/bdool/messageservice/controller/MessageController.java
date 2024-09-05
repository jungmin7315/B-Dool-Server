package com.bdool.messageservice.controller;

import com.bdool.messageservice.model.domain.MessageModel;
import com.bdool.messageservice.model.entity.MessageEntity;
import com.bdool.messageservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageEntity> createMessage(@RequestBody MessageModel message) {
        MessageEntity savedMessage = messageService.save(message);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/channel/{channelId}")
    public ResponseEntity<List<MessageEntity>> getMessagesByChannel(@PathVariable Long channelId) {
        List<MessageEntity> messages = messageService.findAllByChannelId(channelId);
        return ResponseEntity.ok(messages);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable UUID messageId) {
        messageService.deleteById(messageId);
        return ResponseEntity.noContent().build();
    }
}
