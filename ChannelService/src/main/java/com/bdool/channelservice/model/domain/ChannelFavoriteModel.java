package com.bdool.channelservice.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@Component
public class ChannelFavoriteModel {
    private Long favoriteId;

    private Long profileId;

    private Long channelId;

    private Timestamp favoritedAt;
}
