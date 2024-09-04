package com.bdool.channelservice.service.impl;

import com.bdool.channelservice.model.domain.ChannelModel;
import com.bdool.channelservice.model.entity.ChannelEntity;
import com.bdool.channelservice.model.repository.ChannelRepository;
import com.bdool.channelservice.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;

    @Override
    public ChannelEntity save(ChannelModel channel) {
        return channelRepository.save(ChannelEntity.builder()
                .channelId(channel.getChannelId())
                .channelName(channel.getChannelName())
                .isPrivate(channel.isPrivate())
                .workspaceId(channel.getWorkspaceId())
                .profileId(channel.getProfileId())
                .build());
    }

    @Override
    public List<ChannelEntity> findAll() {
        return channelRepository.findAll();
    }

    @Override
    public Optional<ChannelEntity> findById(Long id) {
        return channelRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return channelRepository.existsById(id);
    }

    @Override
    public long count() {
        return channelRepository.count();
    }

    @Override
    public void deleteById(Long id) {
        channelRepository.deleteById(id);
    }
}
