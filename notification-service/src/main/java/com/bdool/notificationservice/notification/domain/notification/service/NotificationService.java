package com.bdool.notificationservice.notification.domain.notification.service;

import com.bdool.notificationservice.notification.domain.notification.entity.Notification;
import com.bdool.notificationservice.notification.domain.notification.entity.model.NotificationModel;

import java.util.List;

public interface NotificationService {

    Notification createNotification(NotificationModel notificationModel);

    List<Notification> findByProfileIdAndReadFalse(Long profileId);

    List<Notification> findByProfileIdOrderByCreatedAtDesc(Long profileId);

    void markAsRead(Long notificationId);

    void cleanupExpiredNotifications();
}
