package com.bdool.bdool.calendar.service.serviceImpl;

import com.bdool.bdool.calendar.model.domain.EventRequest;
import com.bdool.bdool.calendar.model.entity.EventScope;
import com.bdool.bdool.calendar.model.repository.EventRepository;
import com.bdool.bdool.calendar.service.EventService;
import com.bdool.bdool.calendar.service.AttendeeService;
import com.bdool.bdool.calendar.model.entity.EventEntity;
import com.bdool.bdool.calendar.model.entity.AttendeeEntity;
import com.bdool.bdool.calendar.model.entity.AttendeeStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final AttendeeService attendeeService;

    @Override
    public EventEntity createEvent(EventRequest request) {

        EventEntity event = EventEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .hostId(request.getHostId())
                .workspaceId(request.getWorkspaceId())
                .scope(request.getScope())
                .channelId(request.getChannelId())
                .recurrenceType(request.getRecurrenceType())
                .repeatInterval(request.getRepeatInterval())
                .repeatEnd(request.getRepeatEnd())
                .repeatDaysOfWeek(request.getRepeatDaysOfWeek())
                .build();

        EventEntity savedEvent = eventRepository.save(event);

        // AttendeeProfileIds로 참가자 리스트 생성
        List<AttendeeEntity> attendeeEntityList = setParticipants(savedEvent, request.getAttendeeProfileIds());

        // 참가자 저장
        attendeeEntityList.forEach(attendeeService::createAttendee);

        return savedEvent;
    }

    @Override
    public EventEntity updateEvent(Long eventId, EventRequest request) {
        EventEntity existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("해당 일정 없음"));

        EventEntity updatedEvent = existingEvent.builder()
                .id(existingEvent.getId())
                .hostId(existingEvent.getHostId())  // 프로필 ID는 변경되지 않는다고 가정
                .workspaceId(existingEvent.getWorkspaceId()) // 워크스페이스 ID도 변경되지 않는다고 가정
                .title(request.getTitle() != null ? request.getTitle() : existingEvent.getTitle())  // null이면 기존 값 유지
                .description(request.getDescription() != null ? request.getDescription() : existingEvent.getDescription())
                .startTime(request.getStartTime() != null ? request.getStartTime() : existingEvent.getStartTime())
                .endTime(request.getEndTime() != null ? request.getEndTime() : existingEvent.getEndTime())
                .scope(request.getScope() != null ? request.getScope() : existingEvent.getScope())
                .recurrenceType(request.getRecurrenceType() != null ? request.getRecurrenceType() : existingEvent.getRecurrenceType())
                .repeatInterval(request.getRepeatInterval() != 0 ? request.getRepeatInterval() : existingEvent.getRepeatInterval())
                .build();

        // 참가자 싹 삭제하고 다시 생성도 고려해야 함


        return eventRepository.save(updatedEvent);
    }

    @Override
    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);

    }

    @Override
    public Optional<EventEntity> getEventById(Long eventId) {
        return eventRepository.findById(eventId);
    }

    @Override
    public boolean existsEvent(Long eventId) {
        return eventRepository.existsById(eventId);
    }

    @Override
    public long countEvent() {
        return eventRepository.count();
    }


    private List<AttendeeEntity> setParticipants(EventEntity event, List<Long> AttendeeProfileIds) {
        return AttendeeProfileIds.stream()
                .map(profileId -> {
                    AttendeeStatus status = (event.getScope() == EventScope.PERSONAL ? AttendeeStatus.OK : AttendeeStatus.PENDING);
                    return createAttendeeEntity(event, profileId, status);
                })
                .collect(Collectors.toList());
    }


    private AttendeeEntity createAttendeeEntity(EventEntity event, Long profileId, AttendeeStatus status) {
        return AttendeeEntity.builder()
                .profileId(profileId)
                .status(status)
                .event(event)
                .build();
    }
}





