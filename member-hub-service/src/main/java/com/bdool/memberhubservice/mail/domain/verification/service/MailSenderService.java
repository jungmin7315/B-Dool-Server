package com.bdool.memberhubservice.mail.domain.verification.service;

public interface MailSenderService {
    void send(String to, String subject, String body);
}
