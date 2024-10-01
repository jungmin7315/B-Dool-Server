package com.bdool.notificationservice.notification.domain.notification.entity;

import com.bdool.notificationservice.notification.domain.global.JsonConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Table(name = "notifications")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long profileId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Boolean isRead = false;

    @Column
    private LocalDateTime expiresAt;

    @Convert(converter = JsonConverter.class)
    @Column(columnDefinition = "TEXT")
    private Map<String, Object> metadata;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.expiresAt == null) {
            this.expiresAt = createdAt.plusDays(30); // 기본적으로 30일 후에 만료되도록 설정
        }
    }

    public void markAsRead() {
        this.isRead = true;
    }
}