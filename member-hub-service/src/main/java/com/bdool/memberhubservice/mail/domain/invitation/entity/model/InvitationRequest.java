package com.bdool.memberhubservice.mail.domain.invitation.entity.model;

import lombok.Data;

@Data
public class InvitationRequest {
    private Long invitorId;
    private String receiverEmail;
    private Long workspaceId;
}
