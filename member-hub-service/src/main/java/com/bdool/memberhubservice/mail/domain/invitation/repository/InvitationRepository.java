package com.bdool.memberhubservice.mail.domain.invitation.repository;

import com.bdool.memberhubservice.mail.domain.invitation.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    Optional<Invitation> findByReceiverEmailAndInvitationCode(String receiverEmail,String invitationCode);
}
