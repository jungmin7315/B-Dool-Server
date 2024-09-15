package com.bdool.memberhubservice.mail.domain.mail.controller;

import com.bdool.memberhubservice.mail.domain.invitation.entity.model.InvitationRequest;
import com.bdool.memberhubservice.mail.domain.invitation.entity.model.InvitationResponse;
import com.bdool.memberhubservice.mail.domain.invitation.entity.model.InvitationVerifyRequest;
import com.bdool.memberhubservice.mail.domain.invitation.service.InvitationService;
import com.bdool.memberhubservice.mail.domain.verification.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mail")
public class MailSenderController {

    private final VerificationService verificationService;
    private final InvitationService invitationService;

    // 인증코드
    @PostMapping("/send-verification-code")
    public ResponseEntity<Boolean> sendVerificationCode(@RequestParam String email) {
        return ResponseEntity.ok(verificationService.sendVerificationCode(email));
    }

    @PostMapping("/verify-code")
    public ResponseEntity<Boolean> verifyCode(@RequestParam String email,
                                              @RequestParam String verificationCode) {
        return ResponseEntity.ok(verificationService.verifyCode(email, verificationCode));
    }

    // 초대링크
    @PostMapping("/send-invitation")
    public ResponseEntity<Boolean> sendInvitation(@RequestBody InvitationRequest request) {
        return ResponseEntity.ok(invitationService.sendInvitation(request.getInvitorId(), request.getReceiverEmail(), request.getWorkspaceId()));
    }

    @PostMapping("/verify-invitation")
    public ResponseEntity<InvitationResponse> verifyInvitation(@RequestBody InvitationVerifyRequest request) {
        return ResponseEntity.ok(invitationService.verifyInvitation(request.getReceiverEmail(), request.getInvitationCode()));
    }
}
