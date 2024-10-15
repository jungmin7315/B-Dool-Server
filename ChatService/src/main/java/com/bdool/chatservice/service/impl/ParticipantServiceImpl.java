package com.bdool.chatservice.service.impl;

import com.bdool.chatservice.exception.ParticipantIdNotFoundException;
import com.bdool.chatservice.model.domain.ParticipantModel;
import com.bdool.chatservice.model.entity.ChannelEntity;
import com.bdool.chatservice.model.entity.ParticipantEntity;
import com.bdool.chatservice.model.repository.ChannelRepository;
import com.bdool.chatservice.model.repository.ParticipantRepository;
import com.bdool.chatservice.notification.NotificationModel;
import com.bdool.chatservice.notification.NotificationServiceHelper;
import com.bdool.chatservice.notification.NotificationTargetType;
import com.bdool.chatservice.notification.NotificationType;
import com.bdool.chatservice.service.ChannelService;
import com.bdool.chatservice.service.ParticipantService;
import com.bdool.chatservice.sse.ParticipantSSEService;
import com.bdool.chatservice.sse.model.ParticipantNicknameResponse;
import com.bdool.chatservice.sse.model.ParticipantOnlineResponse;
import com.bdool.chatservice.util.UUIDUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.bdool.chatservice.notification.NotificationServiceHelper.sendNotification;

@Service
@RequiredArgsConstructor
@Transactional
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;
    private final ParticipantSSEService sseService;
    private final ChannelRepository channelRepository;
    private final WebClient webClient;
//    @Value("${notification-service.url}")
    private String notificationServiceUrl;

    @Override
    public ParticipantEntity save(ParticipantModel participant) {
        UUID participantId = UUIDUtil.getOrCreateUUID(participant.getParticipantId());
        ParticipantEntity newParticipant = participantRepository.save(
                ParticipantEntity.builder()
                        .participantId(participantId)
                        .nickname(participant.getNickname())
                        .favorited(participant.isFavorited())
                        .joinedAt(LocalDateTime.now())
                        .channelId(participant.getChannelId())
                        .build()
        );
        sendJoinNotificationToChannelParticipants(participant.getChannelId(), participant);

        return newParticipant;
    }

    private void sendJoinNotificationToChannelParticipants(UUID channelId, ParticipantModel newParticipant) {

        List<ParticipantEntity> participants = participantRepository.findByChannelId(channelId);
        ChannelEntity channelEntitiesByChannelId = channelRepository.findChannelEntitiesByChannelId(channelId);
        String channelName = channelEntitiesByChannelId.getName();

        for (ParticipantEntity participant : participants) {
            if (!participant.getParticipantId().equals(newParticipant.getParticipantId())) {
                NotificationModel notificationModel = NotificationServiceHelper.createChannelJoinNotification(
                        newParticipant, channelId, participant.getProfileId(), channelName);

                sendNotification(webClient, notificationServiceUrl, notificationModel);
            }
        }
    }

    @Override
    public ParticipantEntity update(UUID participantId, ParticipantModel participant) {
        return participantRepository.findById(participantId).map(existingMember -> {
            existingMember.setChannelId(participant.getChannelId());
            existingMember.setFavorited(participant.isFavorited());
            existingMember.setNickname(participant.getNickname());
            existingMember.setParticipantId(participant.getParticipantId());

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

    @Override
    public void updateOnline(Long profileId, Boolean isOnline) {
        List<ParticipantEntity> participants = participantRepository.findParticipantEntitiesByProfileId(profileId);

        for (ParticipantEntity participant : participants) {
            participant.updateOnline(isOnline);
            participantRepository.save(participant);

            ParticipantOnlineResponse participantOnlineResponse = new ParticipantOnlineResponse(
                    profileId, participant.getChannelId(), participant.getIsOnline()
            );
            sseService.notifyOnlineChange(participantOnlineResponse);
        }
    }

    @Override
    public void updateNickname(Long profileId, String nickname) {
        List<ParticipantEntity> participants = participantRepository.findParticipantEntitiesByProfileId(profileId);

        for (ParticipantEntity participant : participants) {
            participant.updateNickname(nickname);
            participantRepository.save(participant);

            ParticipantNicknameResponse participantNicknameResponse = new ParticipantNicknameResponse(
                    profileId, participant.getChannelId(), participant.getNickname()
            );
            sseService.notifyNicknameChange(participantNicknameResponse);
        }
    }
}