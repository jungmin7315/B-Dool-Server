package com.bdool.memberhubservice.mail.domain.invitation.repository;

import com.bdool.memberhubservice.mail.domain.invitation.entity.Invitation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    Optional<Invitation> findByReceiverEmailAndInvitationCode(String receiverEmail,String invitationCode);

    @Modifying
    @Transactional
    void deleteAllByExpiresAtBefore(LocalDateTime now);
}
