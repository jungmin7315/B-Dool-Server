package com.bdool.chatservice.model.repository;

import com.bdool.chatservice.model.entity.MessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends MongoRepository<MessageEntity, UUID> {
    List<MessageEntity> findByChannelIdOrderBySendDateAsc(UUID channelId);
    MessageEntity findMessageEntityBy(UUID id);
}

