package com.bdool.notificationservice.notification.domain.notification.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "FCMTokens")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FCMToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 고유 ID

    @Column(nullable = false)
    private Long profileId;  // 프로필 ID 또는 사용자 ID

    @Column(nullable = false, unique = true)
    private String fcmToken;  // FCM 토큰

    @Column(nullable = false)
    private LocalDateTime createdAt;
}