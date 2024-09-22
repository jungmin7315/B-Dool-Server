package com.bdool.memberhubservice.notification.domain.setting.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "settings")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 알림 설정을 가진 사용자 ID
    @Column(nullable = false)
    private Long profileId;

    // 워크스페이스 관련 알림 수신 여부
    @Column(nullable = false)
    private boolean workspaceNotificationsEnabled;

    // 일정 관련 알림 수신 여부
    @Column(nullable = false)
    private boolean calendarNotificationsEnabled;

    // 채널 관련 알림 수신 여부
    @Column(nullable = false)
    private boolean channelNotificationsEnabled;

    // 시스템 알림 수신 여부
    @Column(nullable = false)
    private boolean systemNotificationsEnabled;

    // 푸시 알림을 받을지 여부
    @Column(nullable = false)
    private boolean pushEnabled;

    // 이메일 알림을 받을지 여부
    @Column(nullable = false)
    private boolean emailEnabled;
}
