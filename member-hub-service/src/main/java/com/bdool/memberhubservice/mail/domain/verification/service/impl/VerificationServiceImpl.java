package com.bdool.memberhubservice.mail.domain.verification.service.impl;

import com.bdool.memberhubservice.mail.domain.log.entity.model.LogModel;
import com.bdool.memberhubservice.mail.domain.log.service.LogService;
import com.bdool.memberhubservice.mail.domain.verification.entity.Verification;
import com.bdool.memberhubservice.mail.domain.verification.entity.model.VerificationModel;
import com.bdool.memberhubservice.mail.domain.verification.repository.VerificationRepository;
import com.bdool.memberhubservice.mail.domain.mail.service.MailSenderService;
import com.bdool.memberhubservice.mail.domain.verification.service.VerificationService;
import com.bdool.memberhubservice.member.domain.member.entity.model.MemberModel;
import com.bdool.memberhubservice.member.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class VerificationServiceImpl implements VerificationService {

    private final VerificationRepository verificationRepository;
    private final LogService logService;
    private final MailSenderService mailService;
    private final MemberService memberService;
    private final String SUBJECT = "인증 코드";

    @Override
    public Verification save(VerificationModel verificationModel) {
        Verification verification = Verification.builder()
                .verificationCode(verificationModel.getVerificationCode())
                .email(verificationModel.getEmail())
                .createdAt(LocalDateTime.now())
                .expiredAt(calculateExpirationDate())
                .build();
        return verificationRepository.save(verification);
    }

    @Override
    public Boolean sendVerificationCode(String email) {

        verificationRepository.deleteByEmail(email);

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
    public Boolean verifyCode(String email, String verificationCode) {
        Optional<Verification> verificationOpt = verificationRepository.findByEmail(email);

        if (verificationOpt.isPresent()) {
            Verification verification = verificationOpt.get();
            boolean isCodeValid = verification.getVerificationCode().equals(verificationCode);
            boolean isNotExpired = verification.getExpiredAt().isAfter(LocalDateTime.now());

            if (isCodeValid && isNotExpired) {
                if (!memberService.existsByEmail(email)) {
                    MemberModel memberModel = new MemberModel();
                    memberModel.setEmail(email);
                    memberService.save(memberModel);
                }
                return true;
            }
        }
        return false;
    }

    private String generateVerificationCode() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

    private LocalDateTime calculateExpirationDate() {
        return LocalDateTime.now().plusMinutes(5);  // 변경된 부분
    }

    private Optional<Boolean> sendEmail(String email, String body) {
        try {
            mailService.send(email, SUBJECT, body);
            return Optional.of(true);
        } catch (Exception e) {
            return Optional.of(false);
        }
    }

    @Scheduled(fixedRate = 600000)
    public void deleteExpiredVerifications() {
        verificationRepository.deleteAllExpired(LocalDateTime.now());
    }
}
