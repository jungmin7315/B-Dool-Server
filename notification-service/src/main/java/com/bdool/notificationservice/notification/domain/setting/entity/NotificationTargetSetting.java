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
    private Long profileId;  // 사용자 ID

    @Column(nullable = false)
    private Long targetId;  // 알림 대상의 ID (채널, 워크스페이스, 사용자 등)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationTargetType targetType;  // 알림 대상의 타입 (채널, 사용자, 워크스페이스 등)

    @Column(nullable = false)
    private boolean notificationsEnabled;  // 해당 항목에 대한 알림 활성화 여부

    public void updateNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }
}
