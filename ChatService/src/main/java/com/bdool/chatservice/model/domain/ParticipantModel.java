package com.bdool.chatservice.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Data
public class ParticipantModel {
    private UUID participantId;

    private UUID channelId;

    private String nickname;

    private boolean favorited;

    private LocalDateTime joinedAt;
}
