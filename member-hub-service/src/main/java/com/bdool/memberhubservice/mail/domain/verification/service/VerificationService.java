package com.bdool.memberhubservice.mail.domain.verification.service;

import com.bdool.memberhubservice.mail.domain.verification.entity.Verification;
import com.bdool.memberhubservice.mail.domain.verification.entity.model.VerificationModel;

import java.util.List;
import java.util.Optional;

public interface VerificationService {

    Verification save(VerificationModel verificationModel);

    Optional<Verification> findById(Long verificationId);

    List<Verification> findAll();

    long count();

    boolean existsById(Long verificationId);

    void deleteById(Long verificationId);
}
