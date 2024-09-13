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

    // 메시지를 저장하는 방식
    @Override
    public MessageEntity save(MessageModel message) {
        MessageEntity messageEntity = MessageEntity.builder()
                .messageId(message.getMessageId() == null ? UUID.randomUUID() : message.getMessageId())
                .channelId(message.getChannelId())
                .content(message.getContent())
                .sendDate(LocalDateTime.now())
                .isEdited(false)
                .isDeleted(false)
                .memberId(message.getMemberId())
                .parentMessageId((message.getParentMessageId() == null) ? null : message.getParentMessageId())
                .build();

        return messageRepository.save(messageEntity);
    }

    // 메시지 업데이트
    @Override
    public MessageEntity update(UUID messageId, MessageModel messageModel) {
        Optional<MessageEntity> existingMessageOptional = messageRepository.findById(messageId);

        if (existingMessageOptional.isPresent()) {
            MessageEntity existingMessage = existingMessageOptional.get();
            existingMessage.setContent(messageModel.getContent());
            existingMessage.setIsEdited(true);
            return messageRepository.save(existingMessage);
        } else {
            throw new IllegalArgumentException("Message not found");
        }
    }

    // 모든 메시지 찾
    @Override
    public List<MessageEntity> findAll() {
        return messageRepository.findAll();
    }

    // 채널 ID 기준 모든 메시지 찾기
    @Override
    public List<MessageEntity> findByChannelId(UUID channelId) {
        return messageRepository.findByChannelIdOrderBySendDateAsc(channelId);
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
