package com.bdool.notificationservice.notification.domain.notification.entity.model;

import com.bdool.notificationservice.notification.domain.notification.entity.NotificationType;
import com.bdool.notificationservice.notification.domain.setting.entity.NotificationTargetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationModel {

    private Long profileId;
    private NotificationType notificationType;
    private String message;
    private String metadata;
    private NotificationTargetType targetType;
    private Long targetId;
}
