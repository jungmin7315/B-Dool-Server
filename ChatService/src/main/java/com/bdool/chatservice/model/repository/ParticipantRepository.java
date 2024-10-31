package com.bdool.chatservice.model.repository;

import com.bdool.chatservice.model.entity.ParticipantEntity;
import com.bdool.chatservice.util.UUIDUtil;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParticipantRepository extends MongoRepository<ParticipantEntity, UUID> {
    List<ParticipantEntity> findParticipantEntitiesByProfileId(Long profileId);

    List<ParticipantEntity> findByChannelId(UUID channelId);
    List<ParticipantEntity> findByProfileId(Long profileId);

    boolean existsByChannelIdAndProfileId(UUID channelId, Long profileId);
}
