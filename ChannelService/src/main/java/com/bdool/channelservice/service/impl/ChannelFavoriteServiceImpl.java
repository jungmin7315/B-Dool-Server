package com.bdool.channelservice.service.impl;

import com.bdool.channelservice.model.domain.ChannelFavoriteModel;
import com.bdool.channelservice.model.entity.ChannelFavoriteEntity;
import com.bdool.channelservice.model.repository.ChannelFavoriteRepository;
import com.bdool.channelservice.service.ChannelFavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChannelFavoriteServiceImpl implements ChannelFavoriteService {

    private final ChannelFavoriteRepository channelFavoriteRepository;

    @Override
    public ChannelFavoriteEntity save(ChannelFavoriteModel channelFavorite) {
        ChannelFavoriteEntity entity = ChannelFavoriteEntity.builder()
                .favoriteId(channelFavorite.getFavoriteId())
                .channelId(channelFavorite.getChannelId())
                .profileId(channelFavorite.getProfileId())
                .favoritedAt(channelFavorite.getFavoritedAt())
                .build();
        return channelFavoriteRepository.save(entity);
    }

    @Override
    public List<ChannelFavoriteEntity> findAll() {
        return channelFavoriteRepository.findAll();
    }

    @Override
    public Optional<ChannelFavoriteEntity> findById(Long id) {
        return channelFavoriteRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return channelFavoriteRepository.existsById(id);
    }

    @Override
    public long count() {
        return channelFavoriteRepository.count();
    }

    @Override
    public void deleteById(Long id) {
        channelFavoriteRepository.deleteById(id);
    }
}
