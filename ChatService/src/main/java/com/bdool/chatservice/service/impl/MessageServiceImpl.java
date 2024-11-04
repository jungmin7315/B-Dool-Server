package com.bdool.chatservice.service.impl;

import com.bdool.chatservice.exception.MessageNotFoundException;
import com.bdool.chatservice.model.domain.MessageModel;
import com.bdool.chatservice.model.entity.MessageEntity;
import com.bdool.chatservice.model.entity.MessageReadStatusEntity;
import com.bdool.chatservice.model.repository.MessageReadStatusRepository;
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
    private final MessageReadStatusRepository messageReadStatusRepository;

    // 메시지 저장
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
                .profileId(message.getProfileId())
                .fileUrl(message.getFileURL())
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

    // ID로 메시지 삭제
    @Override
    public void deleteById(UUID messageId) {
        messageRepository.deleteById(messageId);
    }

    // 1. 특정 채널에서 사용자의 안 읽은 메시지 수 계산
    public long countUnreadMessages(UUID channelId, Long profileId) {
        MessageReadStatusEntity readStatus = messageReadStatusRepository.findByProfileIdAndChannelId(profileId, channelId);
        UUID lastReadMessageId = readStatus != null ? readStatus.getLastReadMessageId() : null;

        if (lastReadMessageId == null) {
            // 사용자가 한 번도 읽은 메시지가 없으면 전체 메시지 수 반환
            return messageRepository.countByChannelId(channelId);
        } else {
            // 마지막으로 읽은 메시지 이후의 메시지 수 반환
            return messageRepository.countByChannelIdAndMessageIdGreaterThan(channelId, lastReadMessageId);
        }
    }

    // 2. 사용자가 메시지를 읽을 때 읽음 상태를 업데이트하는 메서드
    public void updateReadStatus(UUID channelId, Long profileId, UUID lastReadMessageId) {
        MessageReadStatusEntity readStatus = messageReadStatusRepository.findByProfileIdAndChannelId(profileId, channelId);

        if (readStatus == null) {
            // 빌더 패턴을 사용하여 새로운 객체 생성
            readStatus = MessageReadStatusEntity.builder()
                    .profileId(profileId)
                    .channelId(channelId)
                    .lastReadMessageId(lastReadMessageId) // 읽은 마지막 메시지 ID 설정
                    .build();
        } else {
            // 기존 객체에 읽은 메시지 ID만 업데이트
            readStatus.setLastReadMessageId(lastReadMessageId);
        }

        messageReadStatusRepository.save(readStatus);
    }

//    @Override
//    public List<Long> findAllProfileIdsInChannel(UUID channelId) {
//        // 채널에 속한 모든 profileId를 반환하는 로직을 추가
//        return userRepository.findAllProfileIdsByChannelId(channelId);
//    }

}
