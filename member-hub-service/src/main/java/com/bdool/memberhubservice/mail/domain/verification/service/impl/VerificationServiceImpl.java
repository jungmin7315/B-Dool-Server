package com.bdool.memberhubservice.mail.domain.verification.service.impl;

import com.bdool.memberhubservice.mail.domain.log.entity.model.LogModel;
import com.bdool.memberhubservice.mail.domain.log.service.LogService;
import com.bdool.memberhubservice.mail.domain.verification.entity.Verification;
import com.bdool.memberhubservice.mail.domain.verification.entity.model.VerificationModel;
import com.bdool.memberhubservice.mail.domain.verification.repository.VerificationRepository;
import com.bdool.memberhubservice.mail.domain.verification.service.MailSenderService;
import com.bdool.memberhubservice.mail.domain.verification.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    private final VerificationRepository verificationRepository;
    private final LogService logService;
    private final MailSenderService mailService;
    private final String SUBJECT = "인증 코드";

    @Override
    public Verification save(VerificationModel verificationModel) {
        Verification verification = Verification.builder()
                .verificationCode(verificationModel.getVerificationCode())
                .email(verificationModel.getEmail())
                .expiredAt(calculateExpirationDate())
                .build();
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

    @Override
    public boolean sendVerificationCode(String email) {
        String verificationCode = generateVerificationCode();
        String body = "인증 코드 : " + verificationCode;
        LogModel logModel = new LogModel();
        logModel.setEmail(email);
        logModel.setSubject(SUBJECT);
        logModel.setBody(body);
        boolean isSent = sendEmail(email, body)
                .orElse(false);
        logService.save(logModel, isSent);

        if (isSent) {
            VerificationModel verificationModel = new VerificationModel();
            verificationModel.setVerificationCode(verificationCode);
            verificationModel.setEmail(email);
            save(verificationModel);
            return true;
        }
        return false;
    }

    @Override
    public boolean verifyCode(String email, String verificationCode) {
        return verificationRepository.findByEmail(email)
                .filter(verification -> !verification.getExpiredAt().before(new Date()))
                .map(verification -> verification.getVerificationCode().equals(verificationCode))
                .orElse(false);
    }

    private String generateVerificationCode() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

    private Date calculateExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 30);
        return calendar.getTime();
    }

    private Optional<Boolean> sendEmail(String email, String body) {
        try {
            mailService.send(email, SUBJECT, body);
            return Optional.of(true);
        } catch (Exception e) {
            return Optional.of(false);
        }
    }
}
