package com.bdool.channelservice.service.impl;

import com.bdool.channelservice.model.domain.ChannelProfileModel;
import com.bdool.channelservice.model.entity.ChannelProfileEntity;
import com.bdool.channelservice.model.entity.ChannelProfileId;
import com.bdool.channelservice.model.repository.ChannelProfileRepository;
import com.bdool.channelservice.service.ChannelProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChannelProfileServiceImpl implements ChannelProfileService {

    private final ChannelProfileRepository channelProfileRepository;


    @Override
    public ChannelProfileEntity save(ChannelProfileModel channelProfile) {

        ChannelProfileId id = ChannelProfileId.builder()
                .channelId(channelProfile.getChannelId())
                .profileId(channelProfile.getProfileId())
                .build();

        ChannelProfileEntity entity = ChannelProfileEntity.builder()
                .id(id)
                .joinedAt(channelProfile.getJoinedAt())
                .build();

        return channelProfileRepository.save(entity);
    }

    @Override
    public List<ChannelProfileEntity> findAll() {
        return channelProfileRepository.findAll();
    }

    @Override
    public Optional<ChannelProfileEntity> findById(ChannelProfileId id) {
        return channelProfileRepository.findById(id);
    }

    @Override
    public boolean existsById(ChannelProfileId id) {
        return channelProfileRepository.existsById(id);
    }

    @Override
    public long count() {
        return channelProfileRepository.count();
    }

    @Override
    public void deleteById(ChannelProfileId id) {
        channelProfileRepository.deleteById(id);
    }
}
