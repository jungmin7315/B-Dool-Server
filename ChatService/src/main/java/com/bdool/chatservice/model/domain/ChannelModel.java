package com.bdool.chatservice.model.domain;

import com.bdool.chatservice.model.Enum.ChannelType;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Data
public class ChannelModel {
    private UUID channelId;

    private Long workspacesId;

    private String name;

    private Boolean isPrivate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String description;

    private Long profileId;

    private ChannelType channelType;
}
