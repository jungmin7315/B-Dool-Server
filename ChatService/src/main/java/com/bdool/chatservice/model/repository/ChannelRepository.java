package com.bdool.chatservice.model.repository;

import com.bdool.chatservice.model.entity.ChannelEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChannelRepository extends MongoRepository<ChannelEntity, UUID> {
    List<ChannelEntity> findAllByWorkspacesId(Long id);
    ChannelEntity findChannelEntitiesByChannelId(UUID channelId);
    ChannelEntity findByWorkspacesIdAndChannelType(Long workspacesId, String channelType);
    boolean existsByWorkspacesIdAndName(Long workspacesId, String name);
    boolean existsByWorkspacesIdAndDmRequestId(Long workspacesId, Long dmRequestId);
}
