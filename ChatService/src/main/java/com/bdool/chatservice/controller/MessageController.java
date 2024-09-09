package com.bdool.chatservice.controller;

import com.bdool.chatservice.model.domain.MessageModel;
import com.bdool.chatservice.model.entity.MessageEntity;
import com.bdool.chatservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("")
    public Mono<ResponseEntity<MessageEntity>> save(@RequestBody MessageModel message) {
        return messageService.save(message)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{messageId}")
    public Mono<ResponseEntity<MessageEntity>> update(@PathVariable UUID messageId, @RequestBody MessageModel message) {
        return messageService.update(messageId, message)
                .map(ResponseEntity::ok);
    }

    @GetMapping("")
    public Flux<MessageEntity> findAll() {
        return messageService.findAll(); // 리액티브 흐름 유지
    }

    @GetMapping("/{messageId}")
    public Mono<ResponseEntity<MessageEntity>> findById(@PathVariable UUID messageId) {
        return messageService.findById(messageId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());  // 값이 없을 경우 204 응답
    }

    @GetMapping("/exists/{messageId}")
    public Mono<ResponseEntity<Boolean>> existsById(@PathVariable UUID messageId) {
        return messageService.existsById(messageId)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/count")
    public Mono<ResponseEntity<Long>> count() {
        return messageService.count()
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{messageId}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable UUID messageId) {
        return messageService.deleteById(messageId)
                .then(Mono.just(ResponseEntity.noContent().build()));  // 삭제 후 204 응답
    }
}
