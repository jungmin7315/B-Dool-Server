package com.bdool.memberhubservice.notification.domain.setting.service;

import com.bdool.memberhubservice.notification.domain.notification.entity.NotificationType;
import com.bdool.memberhubservice.notification.domain.setting.entity.Setting;

import java.util.List;

public interface SettingService {
    Setting save(Long profileId, NotificationType type, boolean enabled);

    Setting updateSetting(Long profileId, NotificationType type, boolean enabled);

    List<Setting> getSettingsByProfileId(Long profileId);
}
