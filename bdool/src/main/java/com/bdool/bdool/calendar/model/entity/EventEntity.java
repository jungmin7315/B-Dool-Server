package com.bdool.bdool.calendar.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "title", nullable = false)
    private String title; // 일정 제목

    @Column(name = "description")
    private String description; // 일정 설명

    @NonNull
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime; // 일정 시작 시간

    @NonNull
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime; // 일정 종료 시간

    @NonNull
    @Column(name = "host_id", nullable = false)
    private Long hostId; // 일정 등록자의 프로필 ID

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "scope", nullable = false)
    private EventScope scope; // 'WORKSPACE', 'CHANNEL', 'PERSONAL'

    @NonNull
    @Column(name = "workspace_id", nullable = false)
    private Long workspaceId; // 워크스페이스 ID

    @Column(name = "channel_id")
    private Long channelId; // 채널 ID

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // 생성 시각

    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // 수정 시각

 // 반복 관련 ///
    @Enumerated(EnumType.STRING)
    private RecurrenceType recurrenceType;
    private LocalDateTime repeatEnd; // 반복 종료일 (null이면 무한 반복)
    private int repeatInterval ; // 반복 주기 (1일, 1주, 1달, 1년)
    // 반복할 요일을 저장
    @ElementCollection(targetClass = DayOfWeek.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "event_days_of_week", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "day_of_week")
    private List<DayOfWeek> repeatDaysOfWeek;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


}
