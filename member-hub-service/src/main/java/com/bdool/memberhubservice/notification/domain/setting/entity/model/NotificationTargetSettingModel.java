package com.bdool.memberhubservice.notification.domain.setting.entity.model;

import com.bdool.memberhubservice.notification.domain.setting.entity.NotificationTargetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationTargetSettingModel {
    private Long profileId;          // 사용자 ID
    private Long targetId;           // 대상 ID (채널, 사용자, 워크스페이스 등)
    private NotificationTargetType targetType;  // 대상의 타입 (채널, 사용자, 워크스페이스 등)
    private boolean notificationsEnabled;  // 알림 활성화 여부
}
