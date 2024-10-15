package com.bdool.chatservice.service;

import com.bdool.chatservice.model.domain.ChannelModel;
import com.bdool.chatservice.model.entity.ChannelEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface ChannelService {
    ChannelEntity save(ChannelModel channel);

    ChannelEntity update(UUID profileId, UUID channelId, ChannelModel channel);

    List<ChannelEntity> findAll();

    List<ChannelEntity> findAllByWorkspacesId(Long id);

    List<ChannelEntity> findAllDefaultChannelsByWorkspacesId(Long workspacesId);

    Optional<ChannelEntity> findById(UUID channelId);

    void deleteById(UUID channelId);
}
