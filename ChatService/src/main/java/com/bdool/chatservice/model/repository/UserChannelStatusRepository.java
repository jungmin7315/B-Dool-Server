package com.bdool.chatservice.model.repository;

import com.bdool.chatservice.model.entity.UserChannelStatusEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface UserChannelStatusRepository extends MongoRepository<UserChannelStatusEntity, UUID> {
    UserChannelStatusEntity findByChannelIdAndUserId(UUID channelId, UUID userId);
}
