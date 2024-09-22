package com.bdool.memberhubservice.notification.domain.setting.service;

import com.bdool.memberhubservice.notification.domain.setting.entity.NotificationTargetSetting;
import com.bdool.memberhubservice.notification.domain.setting.entity.NotificationTargetType;
import com.bdool.memberhubservice.notification.domain.setting.entity.model.NotificationTargetSettingModel;


public interface NotificationTargetSettingService {
    NotificationTargetSetting updateTargetNotificationSetting(NotificationTargetSettingModel model);

    boolean isNotificationEnabledForTarget(Long profileId, Long targetId, NotificationTargetType targetType);
}
