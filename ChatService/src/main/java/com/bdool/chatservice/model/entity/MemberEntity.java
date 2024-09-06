package com.bdool.chatservice.model.entity;

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
@Table(name = "member")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "memberId", updatable = false, nullable = false)
    private UUID memberId;

    @Column(name = "channelId")
    private UUID channelId;

    @Column(name = "profileId")
    private UUID profileId;

    @Column(name = "name")
    private String name;

    @Column(name = "favorited")
    private boolean favorited;

    @Column(name = "joinedAt")
    private LocalDateTime joinedAt;
}
