package com.bdool.chatservice.model.repository;

import com.bdool.chatservice.model.entity.MessageReadStatusEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface MessageReadStatusRepository extends MongoRepository<MessageReadStatusEntity, UUID> {
    MessageReadStatusEntity findByProfileIdAndChannelId(Long profileId, UUID channelId);
}
