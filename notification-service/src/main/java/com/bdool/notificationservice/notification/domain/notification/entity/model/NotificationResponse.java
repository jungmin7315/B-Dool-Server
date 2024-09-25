package com.bdool.notificationservice.notification.domain.notification.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {

    private Long id;
    private Long profileId;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private String metadata;
}
