package com.bdool.memberhubservice.notification.domain.setting.service.impl;

import com.bdool.memberhubservice.notification.domain.notification.entity.NotificationType;
import com.bdool.memberhubservice.notification.domain.setting.entity.Setting;
import com.bdool.memberhubservice.notification.domain.setting.repository.SettingRepository;
import com.bdool.memberhubservice.notification.domain.setting.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SettingServiceImpl implements SettingService {

    private final SettingRepository settingRepository;

    @Override
    public Setting updateSetting(Long profileId, NotificationType type, boolean enabled) {
        Setting findSetting = settingRepository.findByProfileIdAndType(profileId, type)
                .orElse(new Setting(null, type, enabled, profileId));
        findSetting.updateEnabled(enabled);
        return settingRepository.save(findSetting);
    }

    @Override
    public List<Setting> getSettingsByProfileId(Long profileId) {
        return settingRepository.findByProfileId(profileId);
    }
}
