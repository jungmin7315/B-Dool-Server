package com.bdool.memberhubservice.notification.domain.notification.entity.model;

import com.bdool.memberhubservice.notification.domain.notification.entity.NotificationType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationModel {

    private Long profileId;
    private NotificationType notificationType;
    private String message;
    private String metadata;
}
