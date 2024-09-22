package com.bdool.memberhubservice.mail.domain.invitation.entity.model;

import lombok.Data;

@Data
public class InvitationVerifyRequest {
    private String receiverEmail;
    private String invitationCode;
}
