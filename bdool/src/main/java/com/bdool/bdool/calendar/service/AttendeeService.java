package com.bdool.bdool.calendar.service;

import com.bdool.bdool.calendar.model.entity.EventEntity;
import com.bdool.bdool.calendar.model.entity.AttendeeEntity;
import com.bdool.bdool.calendar.model.entity.AttendeeStatus;

import java.util.List;
import java.util.Optional;

public interface AttendeeService {
    AttendeeEntity createAttendee(AttendeeEntity attendee);
    AttendeeEntity updateAttendee(Long eventId,Long profileId,AttendeeStatus status); // 자기 일정 상태변경은 자기만
    Optional<AttendeeEntity> getAttendeeById(Long id);

    List<AttendeeEntity> getAttendees();
    void deleteAttendee(Long id); // 권한 부여 해야함

    // 특정 일정에 속하는 참가자 조회
    List<AttendeeEntity> getAttendeesByEventId(Long eventId);

    // 특정 참가자가 속하는 일정 조회
    List<EventEntity> getEventsForAttendee(Long attendeeId);

    long countAttendeesByEventId(Long eventId);

}