package com.bdool.channelservice.service;

import com.bdool.channelservice.model.domain.ChannelFavoriteModel;
import com.bdool.channelservice.model.entity.ChannelFavoriteEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ChannelFavoriteService {
    ChannelFavoriteEntity save(ChannelFavoriteModel channelFavorite);
    List<ChannelFavoriteEntity> findAll();
    Optional<ChannelFavoriteEntity> findById(Long id);

    boolean existsById(Long id);

    long count();

    void deleteById(Long id);
}
