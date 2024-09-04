package com.bdool.channelservice.service;

import com.bdool.channelservice.model.domain.ChannelProfileModel;
import com.bdool.channelservice.model.entity.ChannelProfileEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ChannelProfileService{
    ChannelProfileEntity save(ChannelProfileModel channelProfile);
    List<ChannelProfileEntity> findAll();
    Optional<ChannelProfileEntity> findById(Long id);

    boolean existsById(Long id);

    long count();

    void deleteById(Long id);
}
