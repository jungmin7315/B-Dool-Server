package com.bdool.memberhubservice.mail.domain.verification.entity.model;

import lombok.Data;

@Data
public class VerificationModel {
    private String email;
    private String verificationCode;
}
