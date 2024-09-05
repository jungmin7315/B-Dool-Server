package com.bdool.memberhubservice.mail.domain.domain.verification.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name = "verifications")
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Verification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;
    private String verificationCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredAt;
}
