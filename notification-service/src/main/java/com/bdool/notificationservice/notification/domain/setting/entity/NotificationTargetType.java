package com.bdool.notificationservice.notification.domain.setting.entity;

public enum NotificationTargetType {
    CHANNEL,       // 특정 채널에 대한 알림 설정
    USER,          // 특정 사용자에 대한 알림 설정
    WORKSPACE,     // 특정 워크스페이스에 대한 알림 설정
    CALENDAR,      // 특정 일정에 대한 알림 설정
    OTHER,
    EVENT// 기타 알림 대상
}
