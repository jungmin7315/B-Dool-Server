package com.bdool.memberhubservice.mail.domain.domain.log.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "logs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String subject;
    private String body;
    private boolean isSent;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date sentAt;

    @PrePersist
    protected void onCreate() {
        this.sentAt = new Date();
    }
}
