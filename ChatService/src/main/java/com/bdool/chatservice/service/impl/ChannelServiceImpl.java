package com.bdool.chatservice.service.impl;

import com.bdool.chatservice.exception.ChannelNotFoundException;
import com.bdool.chatservice.model.Enum.ChannelType;
import com.bdool.chatservice.model.domain.ChannelModel;
import com.bdool.chatservice.model.entity.ChannelEntity;
import com.bdool.chatservice.model.entity.ParticipantEntity;
import com.bdool.chatservice.model.repository.ChannelRepository;
import com.bdool.chatservice.model.repository.ParticipantRepository;
import com.bdool.chatservice.service.ChannelService;
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
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;
    private final ParticipantRepository participantRepository;

    @Override
    @Transactional
    public ChannelEntity save(ChannelModel channelModel) {
        // DM 채널일 경우 추가 검증
        if (channelModel.getChannelType() == ChannelType.DM) {
            if (channelModel.getDmRequestId() == null) {
                throw new IllegalArgumentException("DM channels must have a target profile ID (dmRequestId).");
            }
            if (channelRepository.existsByWorkspacesIdAndDmRequestId(channelModel.getWorkspacesId(), channelModel.getDmRequestId())) {
                throw new IllegalArgumentException("A DM channel already exists with this dmRequestId in the workspace.");
            }
        } else {
            // 일반 채널일 경우 dmRequestId를 null로 설정
            channelModel.setDmRequestId(null);
        }

        // ChannelEntity 생성
        ChannelEntity channelEntity = ChannelEntity.builder()
                .name(channelModel.getName())
                .description(channelModel.getDescription())
                .isPrivate(channelModel.getIsPrivate() != null ? channelModel.getIsPrivate() : true) // 기본값 설정
                .channelType(channelModel.getChannelType())
                .workspacesId(channelModel.getWorkspacesId())
                .profileId(channelModel.getProfileId())
                .dmRequestId(channelModel.getDmRequestId()) // DM일 경우 dmRequestId 설정, 일반 채널은 null
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // 채널 저장
        ChannelEntity savedChannel = channelRepository.save(channelEntity);

        // DM 또는 일반 채널 모두 생성자 정보를 참석자로 저장
        participantRepository.save(
                ParticipantEntity.builder()
                        .participantId(UUIDUtil.getOrCreateUUID(null))
                        .channelId(savedChannel.getChannelId())
                        .isOnline(true)
                        .joinedAt(LocalDateTime.now())
                        .nickname(channelModel.getNickname())
                        .profileId(channelModel.getProfileId())
                        .build()
        );

        return savedChannel;
    }


    @Override
    @Transactional
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
                    } else {
                        throw new ChannelNotFoundException("Workspace ID cannot be null during channel update.");
                    }

                    return channelRepository.save(existingChannel);
                }).orElseThrow(() -> new ChannelNotFoundException("Channel not found with ID: " + channelId));
    }

    @Override
    public List<ChannelEntity> findAll() {
        return channelRepository.findAll();
    }

    @Override
    public List<ChannelEntity> findAllByWorkspacesId(Long id) {
        return channelRepository.findAllByWorkspacesId(id);
    }

    @Override
    public ChannelEntity findDefaultChannelsByWorkspacesId(Long workspacesId) {
        return channelRepository.findByWorkspacesIdAndChannelType(workspacesId, "DEFAULT");
    }

    @Override
    public Optional<ChannelEntity> findById(UUID channelId) {
        return channelRepository.findById(channelId);
    }

    @Override
    @Transactional
    public void deleteById(UUID channelId) {
        if (channelRepository.existsById(channelId)) {
            channelRepository.deleteById(channelId);
        } else {
            throw new RuntimeException("Channel not found with ID: " + channelId);
        }
    }
}
