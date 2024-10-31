package com.bdool.chatservice.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Data
public class ParticipantModel {
    private UUID participantId; //UUID 자동 생성

    private UUID channelId; // 받아야 하는 필드

    private String nickname;

    private LocalDateTime joinedAt; // LocalDateTime.now()로 처리

    private Boolean isOnline;

    private Long profileId;

    private String profileURL;
}
