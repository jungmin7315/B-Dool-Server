package com.bdool.chatservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "message")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "messageId", updatable = false, nullable = false)
    private UUID messageId;

    @Column(name = "memberId",nullable = false)
    private UUID memberId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "isEdited", nullable = false)
    private Boolean isEdited = false;

    @Column(name = "isDeleted", nullable = false)
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentMessageId")
    private MessageEntity parentMessageId;
}
