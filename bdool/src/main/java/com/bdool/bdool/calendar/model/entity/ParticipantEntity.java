package com.bdool.bdool.calendar.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "participants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "profile_id", nullable = false)
    private Long profileId; // 참가자 사용자 ID

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;  // 참가자가 속한 이벤트

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ParticipantStatus status; // 참가 상태 (예: 초대됨, 수락됨, 거절됨)


}
