package com.bdool.memberhubservice.notification.domain.setting.repository;

import com.bdool.memberhubservice.notification.domain.notification.entity.NotificationType;
import com.bdool.memberhubservice.notification.domain.setting.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SettingRepository extends JpaRepository<Setting, Long> {

    Optional<Setting> findByProfileIdAndType(Long profileId, NotificationType type);

    List<Setting> findByProfileId(Long profileId);
}
