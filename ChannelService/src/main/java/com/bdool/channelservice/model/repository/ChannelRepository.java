package com.bdool.channelservice.model.repository;

import com.bdool.channelservice.model.entity.ChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<ChannelEntity,Long> {

}
