package com.bdool.memberhubservice.mail.domain.log.entity.model;

import lombok.Data;

@Data
public class LogModel {
    private String email;
    private String subject;
    private String body;
    private Boolean isSent;
}
