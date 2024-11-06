package com.bdool.bdool.calendar.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "attendees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendeeEntity {
    @Id
    @Column(name = "attendee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "profile_id", nullable = false)
    private Long profileId; // 참가자 사용자 ID

    private String nickname;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;  // 참가자가 속한 이벤트

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AttendeeStatus status; // 참가 상태


}
