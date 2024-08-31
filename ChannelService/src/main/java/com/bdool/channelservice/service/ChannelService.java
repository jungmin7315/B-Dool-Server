package com.bdool.channelservice.service;

import com.bdool.channelservice.model.domain.ChannelModel;
import com.bdool.channelservice.model.entity.ChannelEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ChannelService {
    ChannelEntity save(ChannelModel channel);
    List<ChannelEntity> findAll();
    Optional<ChannelEntity> findById(Long id);

    boolean existsById(Long id);

    long count();

    void deleteById(Long id);
}