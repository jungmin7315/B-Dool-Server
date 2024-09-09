package com.bdool.chatservice.service.impl;

import com.bdool.chatservice.model.domain.MessageModel;
import com.bdool.chatservice.model.entity.MessageEntity;
import com.bdool.chatservice.model.repository.MessageRepository;
import com.bdool.chatservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    // 메시지를 저장하는 리액티브 방식
    @Override
    public Mono<MessageEntity> save(MessageModel message) {
        MessageEntity messageEntity = MessageEntity.builder()
                .messageId(message.getMessageId() == null ? UUID.randomUUID() : message.getMessageId())
                .content(message.getContent())
                .createdAt(LocalDateTime.now())
                .isDeleted(false)
                .isEdited(false)
                .memberId(message.getMemberId())
                .parentMessageId((message.getParentMessageId() != null) ? message.getParentMessageId() : null)
                .build();

        // Mono로 반환 (리액티브 방식으로 insert)
        return messageRepository.insert(messageEntity);
    }

    // 메시지 업데이트도 리액티브 방식으로 수정
    @Override
    public Mono<MessageEntity> update(UUID messageId, MessageModel messageModel) {
        return messageRepository.findById(messageId)
                .flatMap(existingMessage -> {
                    existingMessage.setContent(messageModel.getContent());
                    existingMessage.setIsEdited(true);
                    return messageRepository.save(existingMessage);  // 리액티브 방식으로 save
                })
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Message not found")));  // 메시지가 없을 경우 예외 처리
    }

    // 모든 메시지를 찾기
    @Override
    public Flux<MessageEntity> findAll() {
        return messageRepository.findAll();  // 리액티브 방식으로 처리
    }

    // ID로 메시지를 찾기
    @Override
    public Mono<MessageEntity> findById(UUID messageId) {
        return messageRepository.findById(messageId);
    }

    // ID로 메시지가 존재하는지 확인
    @Override
    public Mono<Boolean> existsById(UUID messageId) {
        return messageRepository.existsById(messageId);
    }

    // 메시지 개수 카운트
    @Override
    public Mono<Long> count() {
        return messageRepository.count();
    }

    // ID로 메시지 삭제
    @Override
    public Mono<Void> deleteById(UUID messageId) {
        return messageRepository.deleteById(messageId);
    }
}
