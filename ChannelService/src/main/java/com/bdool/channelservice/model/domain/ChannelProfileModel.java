package com.bdool.channelservice.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@Component
public class ChannelProfileModel {

    private Long channelId;
    private Long profileId;
    private Timestamp joinedAt;

}