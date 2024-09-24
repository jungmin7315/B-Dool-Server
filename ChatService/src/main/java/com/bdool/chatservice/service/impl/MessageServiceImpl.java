package com.bdool.chatservice.service.impl;

import com.bdool.chatservice.exception.MessageNotFoundException;
import com.bdool.chatservice.model.domain.MessageModel;
import com.bdool.chatservice.model.entity.MessageEntity;
import com.bdool.chatservice.model.repository.MessageRepository;
import com.bdool.chatservice.service.MessageService;
import com.bdool.chatservice.util.UUIDUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    // 메시지를 저장하는 방식
    @Override
    public MessageEntity save(MessageModel message) {
        UUID messageId = UUIDUtil.getOrCreateUUID(message.getMessageId());
        return messageRepository.save(MessageEntity.builder()
                .messageId(messageId)
                .channelId(message.getChannelId())
                .content(message.getContent())
                .sendDate(LocalDateTime.now())
                .isEdited(false)
                .isDeleted(false)
                .participantId(message.getParticipantId())
                .parentMessageId((message.getParentMessageId() == null) ? null : message.getParentMessageId())
                .build());
    }

    // 메시지 업데이트
    @Override
    public MessageEntity update(UUID messageId, MessageModel message) {
        return messageRepository.findById(messageId)
                .map(existingMessage -> {
                    existingMessage.setContent(message.getContent());
                    existingMessage.setIsEdited(true);
                    return messageRepository.save(existingMessage);
                }).orElseThrow(() -> new MessageNotFoundException("Message not found with ID: " + messageId));
    }

    // 모든 메시지 찾기
    @Override
    public List<MessageEntity> findAll() {
        return messageRepository.findAll();
    }

    // 채널 ID 기준 모든 메시지 찾기
    @Override
    public List<MessageEntity> findByChannelId(UUID channelId, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return messageRepository.findByChannelIdOrderBySendDateDesc(channelId,pageable).getContent();
    }

    // ID로 메시지 찾기
    @Override
    public MessageEntity findById(UUID messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));
    }

    // ID로 메시지가 존재하는지 확인
    @Override
    public Boolean existsById(UUID messageId) {
        return messageRepository.existsById(messageId);
    }

    // 메시지 개수 카운트
    @Override
    public Long count() {
        return messageRepository.count();
    }

    // ID로 메시지 삭제
    @Override
    public void deleteById(UUID messageId) {
        messageRepository.deleteById(messageId);
    }
}
