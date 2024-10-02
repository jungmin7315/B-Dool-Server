package com.bdool.memberhubservice.mail.domain.invitation.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvitationResponse {

    private Boolean isValid;
    private Long memberId;
    private Long workspaceId;
}
