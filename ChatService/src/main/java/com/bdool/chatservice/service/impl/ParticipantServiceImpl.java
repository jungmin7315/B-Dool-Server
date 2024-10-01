package com.bdool.chatservice.service.impl;

import com.bdool.chatservice.exception.ParticipantIdNotFoundException;
import com.bdool.chatservice.model.domain.ParticipantModel;
import com.bdool.chatservice.model.entity.ParticipantEntity;
import com.bdool.chatservice.model.repository.ParticipantRepository;
import com.bdool.chatservice.service.ParticipantService;
import com.bdool.chatservice.util.UUIDUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;

    @Override
    public ParticipantEntity save(ParticipantModel participant) {
        UUID participantId = UUIDUtil.getOrCreateUUID(participant.getParticipantId());
        return participantRepository.save(ParticipantEntity.builder()
                .participantId(participantId)
                .profileId(participant.getProfileId())
                .favorited(participant.isFavorited())
                .joinedAt(LocalDateTime.now())
                .channelId(participant.getChannelId())
                .isOnline(participant.isOnline())
                .build());
    }

    @Override
    public ParticipantEntity update(UUID participantId, ParticipantModel participant) {
        return participantRepository.findById(participantId).map(existingMember -> {
            existingMember.setChannelId(participant.getChannelId());
            existingMember.setFavorited(participant.isFavorited());
            existingMember.setProfileId(participant.getProfileId());
            existingMember.setParticipantId(participant.getParticipantId());
            existingMember.setOnline(participant.isOnline());

            return participantRepository.save(existingMember);
        }).orElseThrow(() -> new ParticipantIdNotFoundException("Member not found with ID: " + participantId));
    }

    @Override
    public List<ParticipantEntity> findAll() {
        return participantRepository.findAll();
    }

    @Override
    public Optional<ParticipantEntity> findById(UUID participantId) {
        return participantRepository.findById(participantId);
    }

    @Override
    public boolean existsById(UUID participantId) {
        return participantRepository.existsById(participantId);
    }

    @Override
    public long count() {
        return participantRepository.count();
    }

    @Override
    public void deleteById(UUID participantId) {
        participantRepository.deleteById(participantId);
    }
}