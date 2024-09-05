package com.bdool.messageservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "messageStatus")
public class MessageStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "statusId", updatable = false, nullable = false)
    private Long statusId;

    @Column(name = "messageId", nullable = false)
    private UUID messageId;

    @Column(name = "profileId", nullable = false)
    private Long profileId;

    @Column(name = "isRead", nullable = false)
    private Boolean isRead = false;

    @Column(name = "readAt", updatable = false)
    private LocalDateTime readAt;
}
