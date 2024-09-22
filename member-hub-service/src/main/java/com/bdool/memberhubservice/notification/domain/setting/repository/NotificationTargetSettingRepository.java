package com.bdool.memberhubservice.notification.domain.setting.repository;

import com.bdool.memberhubservice.notification.domain.setting.entity.NotificationTargetSetting;
import com.bdool.memberhubservice.notification.domain.setting.entity.NotificationTargetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationTargetSettingRepository extends JpaRepository<NotificationTargetSetting, Long> {

    Optional<NotificationTargetSetting> findByProfileIdAndTargetIdAndTargetType(Long profileId, Long targetId, NotificationTargetType targetType);
}
