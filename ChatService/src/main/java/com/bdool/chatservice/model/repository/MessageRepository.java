package com.bdool.chatservice.model.repository;

import com.bdool.chatservice.model.entity.MessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends MongoRepository<MessageEntity, UUID> {
    Page<MessageEntity> findByChannelIdOrderBySendDateDesc(UUID channelId, Pageable pageable);
    MessageEntity findMessageEntityBy(UUID id);
    // 특정 채널에서 특정 메시지 ID 이후의 메시지 개수 조회
    long countByChannelIdAndMessageIdGreaterThan(UUID channelId, UUID messageId);
    // 특정 채널의 전체 메시지 개수 조회
    long countByChannelId(UUID channelId);
}

