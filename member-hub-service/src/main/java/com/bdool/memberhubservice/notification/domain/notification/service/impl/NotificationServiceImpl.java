package com.bdool.memberhubservice.notification.domain.notification.service.impl;

import com.bdool.memberhubservice.notification.domain.notification.entity.Notification;
import com.bdool.memberhubservice.notification.domain.notification.entity.model.NotificationModel;
import com.bdool.memberhubservice.notification.domain.notification.repository.NotificationRepository;
import com.bdool.memberhubservice.notification.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public Notification createNotification(NotificationModel notificationModel) {
        // 알림 엔티티 생성
        Notification notification = Notification.builder()
                .profileId(notificationModel.getProfileId())
                .notificationType(notificationModel.getNotificationType())
                .message(notificationModel.getMessage())
                .metadata(notificationModel.getMetadata())
                .expiresAt(getExpiresAt())  // 기본 만료 시간 30일 후 설정
                .isRead(false)  // 기본적으로 읽지 않음으로 설정
                .build();

        return notificationRepository.save(notification);  // 알림 저장
    }

    // 만료 시간을 현재 시간 기준 30일 후로 설정하는 메서드
    private LocalDateTime getExpiresAt() {
        return LocalDateTime.now().plusDays(30);
    }

    @Override
    public List<Notification> findByProfileIdAndReadFalse(Long profileId) {
        // 읽지 않은 알림 조회
        return notificationRepository.findByProfileIdAndReadFalse(profileId);
    }

    @Override
    public List<Notification> findByProfileIdOrderByCreatedAtDesc(Long profileId) {
        // 모든 알림을 생성 시간 순으로 조회
        return notificationRepository.findByProfileIdOrderByCreatedAtDesc(profileId);
    }

    @Override
    public void markAsRead(Long notificationId) {
        // 알림을 조회하여 읽음 처리
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        if (!notification.isRead()) {
            notification.markAsRead(true);  // 읽음 처리 메서드 호출
            notificationRepository.save(notification);  // 변경 사항 저장
        }
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void cleanupExpiredNotifications() {
        LocalDateTime cutoffDate = LocalDateTime.now();
        notificationRepository.deleteExpiredNotifications(cutoffDate);
    }
}