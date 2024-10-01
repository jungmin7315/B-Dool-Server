package com.bdool.notificationservice.notification.domain.notification.entity.model;

import com.bdool.notificationservice.notification.domain.notification.entity.NotificationType;
import com.bdool.notificationservice.notification.domain.setting.entity.NotificationTargetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationModel {

    private Long profileId;
    private String message;
    private Map<String, Object> metadata;
    private NotificationType notificationType;
    private Long targetId;
    private NotificationTargetType targetType;
}
