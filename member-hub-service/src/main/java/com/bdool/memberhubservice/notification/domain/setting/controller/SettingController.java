package com.bdool.memberhubservice.notification.domain.setting.controller;

import com.bdool.memberhubservice.notification.domain.notification.entity.NotificationType;
import com.bdool.memberhubservice.notification.domain.setting.entity.Setting;
import com.bdool.memberhubservice.notification.domain.setting.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/settings")
public class SettingController {

    private final SettingService settingService;

    @PatchMapping("/{profileId}/{type}")
    public ResponseEntity<Setting> updateSetting(
            @PathVariable Long profileId, @PathVariable NotificationType type, @RequestParam boolean enabled) {
        return ResponseEntity.ok(settingService.updateSetting(profileId, type, enabled));
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<List<Setting>> getSettings(@PathVariable Long profileId) {
        return ResponseEntity.ok(settingService.getSettingsByProfileId(profileId));
    }
}
