package com.bdool.memberhubservice.mail.domain.verification.repository;

import com.bdool.memberhubservice.mail.domain.verification.entity.Verification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VerificationRepository extends JpaRepository<Verification, Long> {

    Optional<Verification> findByEmail(String email);

    void deleteByEmail(String email);

    @Modifying
    @Transactional
    @Query("DELETE FROM Verification v WHERE v.expiredAt < :now")
    void deleteAllExpired(@Param("now") LocalDateTime now);
}
