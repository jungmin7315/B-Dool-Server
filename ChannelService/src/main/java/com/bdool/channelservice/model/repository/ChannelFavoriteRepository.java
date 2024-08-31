package com.bdool.channelservice.model.repository;

import com.bdool.channelservice.model.entity.ChannelFavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelFavoriteRepository extends JpaRepository<ChannelFavoriteEntity, Long> {

}
