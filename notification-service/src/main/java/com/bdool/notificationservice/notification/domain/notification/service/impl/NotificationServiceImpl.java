package com.bdool.notificationservice.notification.domain.notification.service.impl;

import com.bdool.notificationservice.notification.domain.notification.entity.Notification;
import com.bdool.notificationservice.notification.domain.notification.entity.model.NotificationModel;
import com.bdool.notificationservice.notification.domain.notification.entity.model.NotificationResponse;
import com.bdool.notificationservice.notification.domain.notification.repository.NotificationRepository;
import com.bdool.notificationservice.notification.domain.notification.service.NotificationService;
import com.bdool.notificationservice.notification.domain.setting.entity.NotificationTargetType;
import com.bdool.notificationservice.notification.domain.setting.service.NotificationTargetSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationTargetSettingService targetSettingService;
    private final NotificationSSEService sseService;

    @Override
    public Notification createNotification(NotificationModel notificationModel) {
        Long profileId = notificationModel.getProfileId();
        Long targetId = notificationModel.getTargetId();
        NotificationTargetType targetType = notificationModel.getTargetType();

        boolean isTargetNotificationEnabled = targetSettingService.isNotificationEnabledForTarget(profileId, targetId, targetType);
        if (!isTargetNotificationEnabled) {
            return null;
        }

        Notification notification = Notification.builder()
                .profileId(profileId)
                .notificationType(notificationModel.getNotificationType())
                .message(notificationModel.getMessage())
                .metadata(notificationModel.getMetadata())
                .isRead(false)
                .build();

        Notification savedNotification = notificationRepository.save(notification);
        NotificationResponse notificationResponse = toNotificationResponse(savedNotification);

        sseService.sendEventToAllEmitters("new-notification", notificationResponse);
        return savedNotification;
    }

    public NotificationResponse toNotificationResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .profileId(notification.getProfileId())
                .notificationType(notification.getNotificationType().toString())  // Enum을 문자열로 변환
                .message(notification.getMessage())
                .metadata(notification.getMetadata())
                .createdAt(notification.getCreatedAt())
                .build();
    }

    @Override
    public List<Notification> findByProfileIdAndReadFalse(Long profileId) {
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

        if (!notification.getIsRead()) {
            notification.markAsRead();  // 읽음 처리 메서드 호출
            notificationRepository.save(notification);  // 변경 사항 저장
        }
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *")  // 매일 자정에 실행
    public void cleanupExpiredNotifications() {
        LocalDateTime cutoffDate = LocalDateTime.now();
        notificationRepository.deleteExpiredNotifications(cutoffDate);
    }
}