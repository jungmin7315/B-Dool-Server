package com.bdool.notificationservice.notification.domain.notification.repository;

import com.bdool.notificationservice.notification.domain.notification.entity.FCMToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FCMTokenRepository extends JpaRepository<FCMToken, Long> {

    Optional<FCMToken> findByProfileId(Long profileId);
}
