package com.bdool.notificationservice.notification.domain.notification.controller;

import com.bdool.notificationservice.notification.domain.notification.service.fcm.FCMService;
import com.bdool.notificationservice.notification.domain.notification.service.fcm.FCMTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/notifications/fcm")
@RequiredArgsConstructor
public class FCMTokenController {

    private final FCMTokenService fcmTokenService;
    private final FCMService fcmService;

    @PostMapping("/token")
    public ResponseEntity<Void> saveFcmToken(@RequestParam Long profileId, @RequestParam String fcmToken) {
        fcmTokenService.saveToken(profileId, fcmToken);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendNotification(@RequestParam Long profileId,
                                                 @RequestParam String title,
                                                 @RequestParam String body) {
        String fcmToken = fcmTokenService.getTokenByProfileId(profileId);
        fcmService.sendNotification(fcmToken, title, body);
        return ResponseEntity.ok().build();
    }
}
