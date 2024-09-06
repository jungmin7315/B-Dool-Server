package com.bdool.chatservice.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Data
public class MemberModel {
    private UUID memberId;

    private UUID channelId;

    private UUID profileId;

    private String name;

    private boolean favorited;

    private LocalDateTime joinedAt;
}
