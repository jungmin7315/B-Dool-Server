package com.bdool.bdool.calendar.controller;

import com.bdool.bdool.calendar.model.entity.EventEntity;
import com.bdool.bdool.calendar.model.entity.AttendeeEntity;
import com.bdool.bdool.calendar.model.entity.AttendeeStatus;
import com.bdool.bdool.calendar.service.AttendeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/attendees")
@RequiredArgsConstructor
public class AttendeeController {

    private final AttendeeService attendeeService;

    // 참가자 생성
    @PostMapping
    public ResponseEntity<AttendeeEntity> createAttendee(@RequestBody AttendeeEntity attendee) {
        AttendeeEntity createdAttendee = attendeeService.createAttendee(attendee);
        return ResponseEntity.ok(createdAttendee);
    }

    // 참가자 상태 수정
    @PutMapping("/{eventId}/{profileId}")
    public ResponseEntity<AttendeeEntity> updateAttendee(@PathVariable Long eventId, @PathVariable Long profileId, @RequestBody AttendeeStatus status) {
        AttendeeEntity updatedParticipant = attendeeService.updateAttendee(eventId,profileId, status);
        return ResponseEntity.ok(updatedParticipant);
    }

    // 특정 참가자 조회
    @GetMapping("/{attendeeId}")
    public ResponseEntity<AttendeeEntity> getAttendee(@PathVariable Long attendeeId) {
        Optional<AttendeeEntity> participantOpt = attendeeService.getAttendeeById(attendeeId);
        return participantOpt.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }
    // 참가자 삭제
    @DeleteMapping("/{attendeeId}")
    public ResponseEntity<Void> deleteAttendee(@PathVariable Long attendeeId) {
        attendeeService.deleteAttendee(attendeeId);
        return ResponseEntity.noContent().build();
    }

    // 특정 이벤트에 대한 참가자 목록 조회
    @GetMapping("/events/{eventId}")
    public ResponseEntity<List<AttendeeEntity>> getEventAttendees(@PathVariable Long eventId) {
        List<AttendeeEntity> participants = attendeeService.getAttendeesByEventId(eventId);
        return ResponseEntity.ok(participants);
    }

    // 특정 이벤트에 대한 참가자 수
    @GetMapping("/events/{eventId}/count")
    public ResponseEntity<Long> countEventAttendees(@PathVariable Long eventId) {
        long participantCount = attendeeService.countAttendeesByEventId(eventId);
        return ResponseEntity.ok(participantCount);
    }

    // 특정 참가자의 일정 목록 조회
    @GetMapping("/{profileId}/events")
    public ResponseEntity<List<EventEntity>> getAttendeeEvents(@PathVariable Long profileId) {
        List<EventEntity> events = attendeeService.getEventsForAttendee(profileId);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/list") //All
    public ResponseEntity<List<AttendeeEntity>> getAllAttendees() {
        List<AttendeeEntity> attendees = attendeeService.getAttendees();
        return ResponseEntity.ok(attendees);
    }
}




