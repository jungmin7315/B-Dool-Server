package com.bdool.workspaceservice.calendar.model.domain;

import com.bdool.workspaceservice.calendar.model.entity.EventScope;
import com.bdool.workspaceservice.calendar.model.entity.RecurrenceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EventRequest {
    private Long hostId;
    private Long workspaceId;

    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    //참가자추가관련
    private EventScope scope;// 일정 범위 (PERSONAL, WORKSPACE, CHANNEL)
    private Long channelId;// 채널 ID (scope이 CHANNEL일 때만)
    private List<Long> attendeeProfileIds;

    //반복일정관련
    private RecurrenceType recurrenceType;
    private int repeatInterval;
    private LocalDateTime repeatEnd;
    private List<DayOfWeek> repeatDaysOfWeek;


}


