package com.bdool.memberhubservice.mail.domain.invitation.entity.model;

import lombok.Data;

@Data
public class InvitationModel {
    private String invitationCode;
    private Long workspaceId;
    private Long inviterId;
    private String invitor;
    private String receiverEmail;
}
