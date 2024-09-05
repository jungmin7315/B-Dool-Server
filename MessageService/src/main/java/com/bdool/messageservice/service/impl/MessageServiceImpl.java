package com.bdool.messageservice.service.impl;

import com.bdool.messageservice.model.domain.MessageModel;
import com.bdool.messageservice.model.entity.MessageEntity;
import com.bdool.messageservice.model.entity.MongoMessageEntity;
import com.bdool.messageservice.model.repository.MessageRepository;
import com.bdool.messageservice.model.repository.MongoMessageRepository;
import com.bdool.messageservice.service.MessageService;
import com.bdool.messageservice.service.RedisMessageService;
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
    private final RedisMessageService redisMessageService;
    private final MongoMessageRepository mongoMessageRepository;

    @Override
    public MessageEntity save(MessageModel message) {
        MessageEntity savedMessage = messageRepository.save(
                MessageEntity.builder()
                        .content(message.getContent())
                        .messageType(message.getMessageType())
                        .channelId(message.getChannelId())
                        .profileId(message.getProfileId())
                        .createdAt(LocalDateTime.now())
                        .isDeleted(false)
                        .isEdited(false)
                        .parentMessageId((message.getParentMessageId() != null) ? message.getParentMessageId() : null)  // 부모 메시지 ID가 있는 경우 설정
                        .build()
        );
        redisMessageService.cacheMessage(savedMessage);
        mongoMessageRepository.save(MongoMessageEntity.builder()
                        .channelId(savedMessage.getChannelId())
                        .profileId(savedMessage.getProfileId())
                        .content(savedMessage.getContent())
                .build());
        return savedMessage;
    }

    @Override
    public List<MessageEntity> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public List<MessageEntity> findAllByChannelId(Long channelId) {
        return messageRepository.findAllByChannelId(channelId);
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
