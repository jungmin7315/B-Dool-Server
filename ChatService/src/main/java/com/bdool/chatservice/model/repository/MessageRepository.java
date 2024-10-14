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
}

