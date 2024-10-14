package com.bdool.chatservice.service.impl;

import com.bdool.chatservice.exception.ChannelNotFoundException;
import com.bdool.chatservice.model.domain.ChannelModel;
import com.bdool.chatservice.model.entity.ChannelEntity;
import com.bdool.chatservice.model.entity.ParticipantEntity;
import com.bdool.chatservice.model.repository.ChannelRepository;
import com.bdool.chatservice.model.repository.ParticipantRepository;
import com.bdool.chatservice.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;
    private final ParticipantRepository participantRepository;  // 참석자 저장을 위한 리포지토리

    @Override
    public ChannelEntity save(ChannelModel channel) {
        // 채널 엔티티 생성
        ChannelEntity channelEntity = ChannelEntity.builder()
                .name(channel.getName())
                .description(channel.getDescription())
                .isPrivate(channel.getIsPrivate())
                .channelType(channel.getChannelType())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .workspacesId(channel.getWorkspacesId())
                .profileId(channel.getProfileId())  // 채널 생성자 정보
                .build();

        // 채널 저장
        ChannelEntity savedChannel = channelRepository.save(channelEntity);

        // 채널 생성자를 참석자로 자동 등록 (nickname 추가)
        ParticipantEntity participant = ParticipantEntity.builder()
                .channelId(savedChannel.getChannelId())
                .nickname(channel.getNickname())  // 채널 생성 시 nickname을 받아서 설정
                .profileId(channel.getProfileId())
                .joinedAt(LocalDateTime.now())
                .isOnline(true)  // 기본값으로 생성자는 온라인 상태로 설정
                .build();

        participantRepository.save(participant);  // 참석자 저장

        return savedChannel;
    }

    @Override
    public ChannelEntity update(UUID channelId, UUID profileId, ChannelModel channel) {
        return channelRepository.findById(channelId)
                .map(existingChannel -> {
                    // 전달받은 profileId와 기존의 profileId가 동일하지 않으면 예외 발생
                    if (!existingChannel.getProfileId().equals(profileId)) {
                        throw new IllegalArgumentException("You are not authorized to update this channel.");
                    }

                    existingChannel.setName(channel.getName());
                    existingChannel.setDescription(channel.getDescription());
                    existingChannel.setIsPrivate(channel.getIsPrivate());
                    existingChannel.setChannelType(channel.getChannelType());
                    existingChannel.setUpdatedAt(LocalDateTime.now());

                    // workspacesId도 값이 null이 아니면 업데이트
                    if (channel.getWorkspacesId() != null) {
                        existingChannel.setWorkspacesId(channel.getWorkspacesId());
                    }

                    return channelRepository.save(existingChannel);
                }).orElseThrow(() -> new ChannelNotFoundException("Channel not found with ID: " + channelId));
    }

    @Override
    public List<ChannelEntity> findAll() {
        return channelRepository.findAll();
    }

    @Override
    public List<ChannelEntity> findAllByWorkspacesId(int id) {
        return channelRepository.findAllByWorkspacesId(id);
    }

    @Override
    public Optional<ChannelEntity> findById(UUID channelId) {
        return channelRepository.findById(channelId);
    }

    @Override
    public void deleteById(UUID channelId) {
        if (channelRepository.existsById(channelId)) {
            channelRepository.deleteById(channelId);
        } else {
            throw new RuntimeException("Channel not found with ID: " + channelId);
        }
    }
}
