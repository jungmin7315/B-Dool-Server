package com.bdool.memberhubservice.mail.domain.invitation.service.impl;

import com.bdool.memberhubservice.mail.domain.invitation.entity.Invitation;
import com.bdool.memberhubservice.mail.domain.invitation.entity.model.InvitationModel;
import com.bdool.memberhubservice.mail.domain.invitation.entity.model.InvitationResponse;
import com.bdool.memberhubservice.mail.domain.invitation.repository.InvitationRepository;
import com.bdool.memberhubservice.mail.domain.invitation.service.InvitationService;
import com.bdool.memberhubservice.mail.domain.log.entity.model.LogModel;
import com.bdool.memberhubservice.mail.domain.log.service.LogService;
import com.bdool.memberhubservice.mail.domain.mail.service.MailSenderService;
import com.bdool.memberhubservice.member.domain.member.entity.Member;
import com.bdool.memberhubservice.member.domain.member.entity.model.MemberModel;
import com.bdool.memberhubservice.member.domain.member.service.MemberService;
import com.bdool.memberhubservice.member.domain.profile.entity.Profile;
import com.bdool.memberhubservice.member.domain.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final LogService logService;
    private final MailSenderService mailSenderService;
    private final MemberService memberService;
    private final ProfileService profileService;
    private final String SUBJECT = "초대 링크";


    @Override
    public Invitation save(InvitationModel invitationModel, Profile profile) {
        Invitation invitation = Invitation.builder()
                .inviter(profile.getName())
                .workspaceId(invitationModel.getWorkspaceId())
                .inviterId(profile.getId())
                .invitationCode(invitationModel.getInvitationCode())
                .receiverEmail(invitationModel.getReceiverEmail())
                .createdAt(LocalDateTime.now())
                .expiresAt(calculateExpirationDate())
                .build();
        return invitationRepository.save(invitation);
    }

    @Override
    public boolean sendInvitation(Long invitorId, String receiverEmail, Long workspaceId) {
        // **초대 코드 생성**
        String invitationCode = UUID.randomUUID().toString();
        // **초대 링크 생성**
        String invitationLink = "https://bdool.site/invite?code=" + invitationCode;
        // **이메일 본문 작성**
        Profile profile = profileService.findProfileById(invitorId)
                .orElseThrow();
        String body = profile.getName() + "님이 워크스페이스에 초대하셨습니다.\n아래 링크를 클릭하여 참여하세요:\n" + invitationLink;
        LogModel logModel = new LogModel();
        logModel.setSubject(SUBJECT);
        logModel.setBody(body);
        boolean isSent = sendEmail(receiverEmail, body);
        logService.save(logModel, isSent);
        if (isSent) {
            InvitationModel invitationModel = new InvitationModel();
            invitationModel.setInviterId(profile.getId());
            invitationModel.setInvitor(profile.getName());
            invitationModel.setReceiverEmail(receiverEmail);
            invitationModel.setWorkspaceId(workspaceId);
            invitationModel.setInvitationCode(invitationCode);
            save(invitationModel, profile);
            return true;
        }
        return false;
    }

    @Override
    public InvitationResponse verifyInvitation(String receiverEmail, String invitationCode) {
        Invitation invitation = invitationRepository.findByReceiverEmailAndInvitationCode(receiverEmail, invitationCode)
                .orElseThrow(() -> new NoSuchElementException("초대를 찾을 수 없습니다. 이메일과 초대 코드를 확인해주세요."));

        boolean isNotExpired = invitation.getExpiresAt().isAfter(LocalDateTime.now());  // 변경된 부분
        if (!isNotExpired) {
            throw new IllegalStateException("초대가 만료되었습니다.");
        }

        Member member = memberService.findMemberByEmail(receiverEmail)
                .orElseGet(() -> memberService.save(new MemberModel(receiverEmail)));

        return new InvitationResponse(true, member.getId(), invitation.getWorkspaceId());
    }

    @Scheduled(fixedRate = 86400000)
    public void deleteExpiredInvitations() {
        invitationRepository.deleteAllByExpiresAtBefore(LocalDateTime.now());
    }

    private LocalDateTime calculateExpirationDate() {
        return LocalDateTime.now().plusDays(7);  // 변경된 부분
    }

    private boolean sendEmail(String email, String body) {
        try {
            mailSenderService.send(email, SUBJECT, body);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
