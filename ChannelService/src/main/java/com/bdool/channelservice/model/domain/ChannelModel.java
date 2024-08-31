package com.bdool.channelservice.model.domain;

import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
public class ChannelModel {
    private Long channelId;

    private String channelName;

    private boolean isPrivate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long workspaceId;

    private Long profileId;
}
