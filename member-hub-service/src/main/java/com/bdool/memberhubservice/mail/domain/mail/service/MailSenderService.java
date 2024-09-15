package com.bdool.memberhubservice.mail.domain.mail.service;

public interface MailSenderService {
    void send(String to, String subject, String body);
}
