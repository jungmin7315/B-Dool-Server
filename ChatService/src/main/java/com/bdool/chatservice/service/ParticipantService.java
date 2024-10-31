package com.bdool.chatservice.service;

import com.bdool.chatservice.model.domain.ParticipantModel;
import com.bdool.chatservice.model.entity.ParticipantEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface ParticipantService {
    ParticipantEntity save(ParticipantModel participant);

    ParticipantEntity update(UUID participantId, ParticipantModel participant);

    List<ParticipantEntity> findAll();

    Optional<ParticipantEntity> findById(UUID participantId);

    List<ParticipantEntity> findByChannelId(UUID channelId);

    boolean existsById(UUID participantId);

    boolean isParticipantInChannel(UUID channelId, Long profileId);

    long count();

    void deleteById(UUID participantId);

    void updateOnline(Long profileId, Boolean isOnline);

    void updateNickname(Long profileId, String nickname);

    void updatePorfileURL(Long profileId, String profileURL);
}
