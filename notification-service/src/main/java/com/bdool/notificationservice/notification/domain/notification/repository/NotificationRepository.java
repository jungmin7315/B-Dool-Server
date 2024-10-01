package com.bdool.notificationservice.notification.domain.notification.repository;

import com.bdool.notificationservice.notification.domain.notification.entity.Notification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByProfileIdAndIsReadFalse(Long profileId);

    List<Notification> findByProfileIdOrderByCreatedAtDesc(Long profileId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Notification n WHERE n.expiresAt IS NOT NULL AND n.expiresAt < :cutoffDate")
    void deleteExpiredNotifications(@Param("cutoffDate") LocalDateTime cutoffDate);

}
