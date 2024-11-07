package com.bdool.workspaceservice.calendar.service.serviceImpl;

import com.bdool.workspaceservice.calendar.model.entity.EventEntity;
import com.bdool.workspaceservice.calendar.model.entity.AttendeeEntity;
import com.bdool.workspaceservice.calendar.model.entity.AttendeeStatus;
import com.bdool.workspaceservice.calendar.model.repository.EventRepository;
import com.bdool.workspaceservice.calendar.model.repository.AttendeeRepository;
import com.bdool.workspaceservice.calendar.service.AttendeeService;
import com.bdool.workspaceservice.notification.NotificationModel;
import com.bdool.workspaceservice.notification.NotificationServiceHelper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

import static com.bdool.workspaceservice.notification.NotificationServiceHelper.sendNotification;

@Service
@RequiredArgsConstructor
public class AttendeeServiceImpl implements AttendeeService {
    private final AttendeeRepository attendeeRepository;
    private final EventRepository eventRepository;
    private final WebClient webClient;
    @Value("${notification-service.url}")
    private String notificationServiceUrl;

    @Override
    public AttendeeEntity createAttendee(AttendeeEntity attendeeEntity) {
        AttendeeEntity attendee = AttendeeEntity.builder()
                .event(attendeeEntity.getEvent())
                .profileId(attendeeEntity.getProfileId())
                .nickname(attendeeEntity.getNickname())
                .status(attendeeEntity.getStatus())
                .build();
        sendJoinNotificationToAttendee(attendee);
        return attendeeRepository.save(attendee);
    }

    private void sendJoinNotificationToAttendee(AttendeeEntity attendee) {

        NotificationModel notificationModel = NotificationServiceHelper.createEventJoinNotification(
                attendee, attendee.getProfileId()
        );
        NotificationServiceHelper.sendNotification(webClient, notificationServiceUrl, notificationModel);
    }

    @Override
    public AttendeeEntity updateAttendee(Long eventId, Long profileId, AttendeeStatus status) {

        AttendeeEntity existingAttendee = attendeeRepository.findByEventIdAndProfileId(eventId, profileId)
                .orElseThrow(() -> new EntityNotFoundException("해당 참가자가 없습니다."));

        existingAttendee.setStatus(status);

        return attendeeRepository.save(existingAttendee);
    }

    @Override
    public Optional<AttendeeEntity> getAttendeeById(Long id) {
        return attendeeRepository.findById(id);
    }


    @Override
    public void deleteAttendee(Long id) {
        AttendeeEntity attendee = attendeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 참가자가 없습니다."));

        /*EventEntity event = participant.getEvent();
        if (!event.getProfileId().equals(currentUserId)) {
            throw new SecurityException("삭제할 권한이 없다");
        }*/

        attendeeRepository.delete(attendee);
    }

    @Override
    public List<AttendeeEntity> getAttendeesByEventId(Long eventId) {
        return attendeeRepository.findByEventId(eventId);
    }

    @Override
    public long countAttendeesByEventId(Long eventId) {
        return attendeeRepository.countByEventId(eventId);
    }

    @Override
    public List<EventEntity> getEventsForAttendee(Long profileId) {
        List<AttendeeEntity> participants = attendeeRepository.findByProfileId(profileId);
        return participants.stream()
                .map(AttendeeEntity::getEvent)
                .toList();
    }

    @Override
    public List<AttendeeEntity> getAttendees() {
        return attendeeRepository.findAll();
    }
}
