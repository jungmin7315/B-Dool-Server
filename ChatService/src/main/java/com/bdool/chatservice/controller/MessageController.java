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
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody MessageModel message) {
        return ResponseEntity.ok(messageService.save(message));
    }

    @PutMapping("/{messageId}")
    public ResponseEntity<?> update(@PathVariable UUID messageId, @RequestBody MessageModel message) {
        return ResponseEntity.ok(messageService.update(messageId, message));
    }

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        List<MessageEntity> messages = messageService.findAll();
        if(messages.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(messages);
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<?> findById(@PathVariable UUID messageId) {
        return ResponseEntity.ok(messageService.findById(messageId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build()));
    }

    @GetMapping("/exists/{sessionId}")
    public ResponseEntity<?> existsById(@PathVariable UUID messageId) {
        return ResponseEntity.ok(messageService.existsById(messageId));
    }

    @GetMapping("/count")
    public ResponseEntity<?> count() {
        return ResponseEntity.ok(messageService.count());
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<?> deleteById(@PathVariable UUID messageId) {
        messageService.deleteById(messageId);
        return ResponseEntity.noContent().build();
    }
}
