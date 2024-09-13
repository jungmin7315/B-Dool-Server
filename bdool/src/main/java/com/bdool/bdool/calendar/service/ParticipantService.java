package com.bdool.bdool.calendar.service;

import com.bdool.bdool.calendar.model.entity.EventEntity;
import com.bdool.bdool.calendar.model.entity.ParticipantEntity;
import com.bdool.bdool.calendar.model.entity.ParticipantStatus;

import java.util.List;
import java.util.Optional;

public interface ParticipantService {
    ParticipantEntity createParticipant(ParticipantEntity participant);
    ParticipantEntity updateParticipant(Long id, ParticipantStatus status); // 자기 일정 상태변경은 자기만
    Optional<ParticipantEntity> getParticipantById(Long id);

    List<ParticipantEntity> getParticipants();
    void deleteParticipant(Long id); // 권한 부여 해야함

    // 특정 일정에 속하는 참가자 조회
    List<ParticipantEntity> getParticipantsByEventId(Long eventId);

    // 특정 참가자가 속하는 일정 조회
    List<EventEntity> getEventsForParticipant(Long participantId);

    long countParticipantsByEventId(Long eventId);

}