package com.bdool.notificationservice.notification.domain.setting.entity;

import jakarta.persistence.*;
import lombok.*;

import static lombok.AccessLevel.*;

@Entity
@Table(name = "notification_target_settings")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class NotificationTargetSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long profileId;

    @Column(nullable = false)
    private Long targetId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationTargetType targetType;

    @Column(nullable = false)
    private boolean notificationsEnabled;

    public void updateNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }
}
