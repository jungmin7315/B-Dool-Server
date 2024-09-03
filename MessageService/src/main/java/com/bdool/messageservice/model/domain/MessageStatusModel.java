package com.bdool.messageservice.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Data
public class MessageStatusModel {
    private Long statusId;

    private UUID messageId;

    private Long profileId;

    private Boolean isRead = false;

    private LocalDateTime readAt;
}
