package com.bdool.chatservice.service.impl;

import com.bdool.chatservice.model.domain.MessageModel;
import com.bdool.chatservice.model.entity.MessageEntity;
import com.bdool.chatservice.model.repository.MessageRepository;
import com.bdool.chatservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public MessageEntity save(MessageModel message) {
        return messageRepository.save(
                MessageEntity.builder()
                        .content(message.getContent())
                        .createdAt(LocalDateTime.now())
                        .isDeleted(false)
                        .isEdited(false)
                        .parentMessageId((message.getParentMessage() != null) ? message.getParentMessage() : null)  // 부모 메시지 ID가 있는 경우 설정
                        .build()
        );
    }

    @Override
    public MessageEntity update(UUID messageId, MessageModel message) {
        return null;
    }

    @Override
    public List<MessageEntity> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Optional<MessageEntity> findById(UUID messageId) {
        return messageRepository.findById(messageId);
    }

    @Override
    public boolean existsById(UUID messageId) {
        return messageRepository.existsById(messageId);
    }

    @Override
    public long count() {
        return messageRepository.count();
    }

    @Override
    public void deleteById(UUID messageId) {
        messageRepository.deleteById(messageId);
    }
}
