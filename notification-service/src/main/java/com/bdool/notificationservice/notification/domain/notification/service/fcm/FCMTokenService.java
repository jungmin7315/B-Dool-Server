package com.bdool.notificationservice.notification.domain.notification.service.fcm;

import com.bdool.notificationservice.notification.domain.notification.entity.FCMToken;
import com.bdool.notificationservice.notification.domain.notification.repository.FCMTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FCMTokenService {

    private final FCMTokenRepository fcmTokenRepository;

    public void saveToken(Long profileId, String fcmToken) {
        FCMToken token = FCMToken.builder()
                .profileId(profileId)
                .fcmToken(fcmToken)
                .createdAt(LocalDateTime.now())
                .build();

        fcmTokenRepository.save(token);
    }

    public String getTokenByProfileId(Long profileId) {
        return fcmTokenRepository.findByProfileId(profileId)
                .map(FCMToken::getFcmToken)
                .orElseThrow(() -> new IllegalArgumentException("FCM 토큰을 찾을 수 없습니다."));
    }
}
