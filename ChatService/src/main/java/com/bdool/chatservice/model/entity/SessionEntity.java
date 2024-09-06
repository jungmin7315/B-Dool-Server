package com.bdool.chatservice.model.entity;

import com.bdool.chatservice.model.Enum.SessionType;
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
@Table(name = "session")
public class SessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "sessionId", updatable = false, nullable = false)
    private UUID sessionId;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @Enumerated(EnumType.STRING)
    @Column(name = "session_type", nullable = false)
    private SessionType sessionType;

    @Column(name = "chaneel_member_id", nullable = false)
    private UUID channelMemberId;
}
