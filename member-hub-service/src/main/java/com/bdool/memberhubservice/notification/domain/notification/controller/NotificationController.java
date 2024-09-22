package com.bdool.memberhubservice.notification.domain.notification.controller;

import com.bdool.memberhubservice.notification.domain.notification.entity.Notification;
import com.bdool.memberhubservice.notification.domain.notification.entity.model.NotificationModel;
import com.bdool.memberhubservice.notification.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody NotificationModel notificationModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.createNotification(notificationModel));
    }

    @GetMapping("/unread/{profileId}")
    public ResponseEntity<List<Notification>> getUnreadNotifications(@PathVariable Long profileId) {
        List<Notification> unreadNotifications = notificationService.findByProfileIdAndReadFalse(profileId);
        return ResponseEntity.ok(unreadNotifications);
    }

    @GetMapping("/all/{profileId}")
    public ResponseEntity<List<Notification>> getAllNotifications(@PathVariable Long profileId) {
        List<Notification> allNotifications = notificationService.findByProfileIdOrderByCreatedAtDesc(profileId);
        return ResponseEntity.ok(allNotifications);
    }

    @PostMapping("/{notificationId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok().build();
    }
}
