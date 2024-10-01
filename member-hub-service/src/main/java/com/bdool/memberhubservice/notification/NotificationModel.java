package com.bdool.memberhubservice.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationModel {
    private Long profileId;
    private String message;
    private Map<String, Object> metadata;
    private NotificationType notificationType;
    private Long targetId;
    private NotificationTargetType targetType;
}
