package com.bdool.memberhubservice.notification.domain.notification.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name = "notifications")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    private String type;

    private Long profileId;
}
