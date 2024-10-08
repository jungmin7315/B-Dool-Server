package com.bdool.notificationservice.notification.domain.setting.service.impl;

import com.bdool.notificationservice.notification.domain.setting.entity.NotificationTargetSetting;
import com.bdool.notificationservice.notification.domain.setting.entity.NotificationTargetType;
import com.bdool.notificationservice.notification.domain.setting.entity.model.NotificationTargetSettingModel;
import com.bdool.notificationservice.notification.domain.setting.repository.NotificationTargetSettingRepository;
import com.bdool.notificationservice.notification.domain.setting.service.NotificationTargetSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationTargetSettingServiceImpl implements NotificationTargetSettingService {

    private final NotificationTargetSettingRepository targetSettingRepository;

    @Override
    public NotificationTargetSetting updateTargetNotificationSetting(NotificationTargetSettingModel model) {
        NotificationTargetSetting setting = targetSettingRepository
                .findByProfileIdAndTargetIdAndTargetType(model.getProfileId(), model.getTargetId(), model.getTargetType())
                .orElse(NotificationTargetSetting.builder()
                        .profileId(model.getProfileId())
                        .targetId(model.getTargetId())
                        .targetType(model.getTargetType())
                        .build());

        setting.updateNotificationsEnabled(model.getNotificationsEnabled());
        return targetSettingRepository.save(setting);
    }

    @Override
    public boolean isNotificationEnabledForTarget(Long profileId, Long targetId, NotificationTargetType targetType) {
        return targetSettingRepository.findByProfileIdAndTargetIdAndTargetType(profileId, targetId, targetType)
                .map(NotificationTargetSetting::getNotificationsEnabled)
                .orElseGet(() -> {
                    NotificationTargetSetting defaultSetting = NotificationTargetSetting.builder()
                            .profileId(profileId)
                            .targetId(targetId)
                            .targetType(targetType)
                            .notificationsEnabled(true)  // 기본값: 알림 활성화
                            .build();
                    targetSettingRepository.save(defaultSetting);
                    return true;  // 기본값 true 반환
                });
    }
}
