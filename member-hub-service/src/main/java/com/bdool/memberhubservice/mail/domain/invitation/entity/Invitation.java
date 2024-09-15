package com.bdool.memberhubservice.mail.domain.invitation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name = "invitations")
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String invitationCode;
    private Long inviterId;
    private String inviter;
    private String receiverEmail;
    private Long workspaceId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiresAt;

}
