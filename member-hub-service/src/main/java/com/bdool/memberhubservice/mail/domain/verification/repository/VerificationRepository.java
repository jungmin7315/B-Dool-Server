package com.bdool.memberhubservice.mail.domain.verification.repository;

import com.bdool.memberhubservice.mail.domain.verification.entity.Verification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationRepository extends JpaRepository<Verification, Long> {

    Optional<Verification> findByEmail(String email);
}
