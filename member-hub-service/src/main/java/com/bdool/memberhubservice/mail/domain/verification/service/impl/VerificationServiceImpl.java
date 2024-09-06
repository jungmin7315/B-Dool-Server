package com.bdool.memberhubservice.mail.domain.verification.service.impl;

import com.bdool.memberhubservice.mail.domain.verification.entity.Verification;
import com.bdool.memberhubservice.mail.domain.verification.entity.model.VerificationModel;
import com.bdool.memberhubservice.mail.domain.verification.repository.VerificationRepository;
import com.bdool.memberhubservice.mail.domain.verification.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    private final VerificationRepository verificationRepository;

    @Override
    public Verification save(VerificationModel verificationModel) {
        Verification verification = Verification.builder().build();
        return verificationRepository.save(verification);
    }

    @Override
    public Optional<Verification> findById(Long verificationId) {
        return verificationRepository.findById(verificationId);
    }

    @Override
    public List<Verification> findAll() {
        return verificationRepository.findAll();
    }

    @Override
    public long count() {
        return verificationRepository.count();
    }

    @Override
    public boolean existsById(Long verificationId) {
        return verificationRepository.existsById(verificationId);
    }

    @Override
    public void deleteById(Long verificationId) {
        verificationRepository.deleteById(verificationId);
    }
}
