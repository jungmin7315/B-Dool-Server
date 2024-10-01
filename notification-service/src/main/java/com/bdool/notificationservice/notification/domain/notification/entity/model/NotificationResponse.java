package com.bdool.notificationservice.notification.domain.notification.entity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {

    private Long id;
    private Long profileId;
    private String notificationType;
    private String message;
    private Map<String, Object> metadata;
    private LocalDateTime createdAt;

}
