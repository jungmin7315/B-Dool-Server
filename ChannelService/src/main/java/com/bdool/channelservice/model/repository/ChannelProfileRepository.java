package com.bdool.channelservice.model.repository;

import com.bdool.channelservice.model.entity.ChannelProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelProfileRepository extends JpaRepository<ChannelProfileEntity, Long> {
}
