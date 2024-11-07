package com.bdool.workspaceservice.notification;

public enum NotificationTargetType {
    CHANNEL,       // 특정 채널에 대한 알림 설정
    USER,          // 특정 사용자에 대한 알림 설정
    WORKSPACE,     // 특정 워크스페이스에 대한 알림 설정
    CALENDAR,      // 특정 일정에 대한 알림 설정
    EVENT, OTHER
}
