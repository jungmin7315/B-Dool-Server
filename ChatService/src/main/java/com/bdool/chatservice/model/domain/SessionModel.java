package com.bdool.chatservice.model.domain;

import com.bdool.chatservice.model.Enum.SessionType;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Data
public class SessionModel {
    private UUID sessionId;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    private Long profileId;

    private SessionType sessionType;

    private UUID channelMemberId;
}
