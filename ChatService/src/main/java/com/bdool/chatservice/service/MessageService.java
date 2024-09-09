package com.bdool.chatservice.service;

import com.bdool.chatservice.model.domain.MessageModel;
import com.bdool.chatservice.model.entity.MessageEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Service
public interface MessageService {
    Mono<MessageEntity> save(MessageModel message);

    Mono<MessageEntity> update(UUID messageId, MessageModel message);

    Flux<MessageEntity> findAll();

    Mono<MessageEntity> findById(UUID messageId);

    Mono<Boolean> existsById(UUID messageId);

    Mono<Long> count();

    Mono<Void> deleteById(UUID messageId);
}