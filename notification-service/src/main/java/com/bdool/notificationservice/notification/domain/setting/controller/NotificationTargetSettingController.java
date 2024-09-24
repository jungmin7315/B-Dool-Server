package com.bdool.notificationservice.notification.domain.setting.controller;

import com.bdool.notificationservice.notification.domain.setting.entity.NotificationTargetSetting;
import com.bdool.notificationservice.notification.domain.setting.entity.NotificationTargetType;
import com.bdool.notificationservice.notification.domain.setting.entity.model.NotificationTargetSettingModel;
import com.bdool.notificationservice.notification.domain.setting.service.NotificationTargetSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification-settings")
@RequiredArgsConstructor
public class NotificationTargetSettingController {

    private final NotificationTargetSettingService targetSettingService;

    @PostMapping("/update")
    public ResponseEntity<NotificationTargetSetting> updateTargetNotificationSetting(@RequestBody NotificationTargetSettingModel model) {
        return ResponseEntity.ok(targetSettingService.updateTargetNotificationSetting(model));
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> isNotificationEnabledForTarget(
            @RequestParam Long profileId,
            @RequestParam Long targetId,
            @RequestParam NotificationTargetType targetType) {
        return ResponseEntity.ok(targetSettingService.isNotificationEnabledForTarget(profileId, targetId, targetType));
    }
}
