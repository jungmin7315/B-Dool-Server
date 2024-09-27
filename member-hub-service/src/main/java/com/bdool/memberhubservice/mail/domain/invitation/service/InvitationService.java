package com.bdool.memberhubservice.mail.domain.invitation.service;

import com.bdool.memberhubservice.mail.domain.invitation.entity.Invitation;
import com.bdool.memberhubservice.mail.domain.invitation.entity.model.InvitationModel;
import com.bdool.memberhubservice.mail.domain.invitation.entity.model.InvitationResponse;
import com.bdool.memberhubservice.member.domain.profile.entity.Profile;

public interface InvitationService {

    Invitation save(InvitationModel invitationModel, Profile profile);

    boolean sendInvitation(Long invitorId, String receiverEmail, Long workspaceId);

    InvitationResponse verifyInvitation(String receiverEmail, String code);
}
