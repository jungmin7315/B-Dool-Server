package com.bdool.bdool.calendar.controller;

import com.bdool.bdool.calendar.model.entity.EventEntity;
import com.bdool.bdool.calendar.model.entity.ParticipantEntity;
import com.bdool.bdool.calendar.model.entity.ParticipantStatus;
import com.bdool.bdool.calendar.service.ParticipantService;
import com.bdool.bdool.workspace.model.WorkspaceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/participants")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;

    // 참가자 생성
    @PostMapping
    public ResponseEntity<ParticipantEntity> createParticipant(@RequestBody ParticipantEntity participantEntity) {
        ParticipantEntity createdParticipant = participantService.createParticipant(participantEntity);
        return ResponseEntity.ok(createdParticipant);
    }

    // 참가자 상태 수정
    @PutMapping("/{participantId}")
    public ResponseEntity<ParticipantEntity> updateParticipant(@PathVariable Long participantId, @RequestBody ParticipantStatus status) {
        ParticipantEntity updatedParticipant = participantService.updateParticipant(participantId, status);
        return ResponseEntity.ok(updatedParticipant);
    }

    // 특정 참가자 조회
    @GetMapping("/{participantId}")
    public ResponseEntity<ParticipantEntity> getParticipant(@PathVariable Long participantId) {
        Optional<ParticipantEntity> participantOpt = participantService.getParticipantById(participantId);
        return participantOpt.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }
    // 참가자 삭제
    @DeleteMapping("/{participantId}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable Long participantId) {
        participantService.deleteParticipant(participantId);
        return ResponseEntity.noContent().build();
    }

    // 특정 이벤트에 대한 참가자 목록 조회
    @GetMapping("/events/{eventId}")
    public ResponseEntity<List<ParticipantEntity>> getEventParticipants(@PathVariable Long eventId) {
        List<ParticipantEntity> participants = participantService.getParticipantsByEventId(eventId);
        return ResponseEntity.ok(participants);
    }

    // 특정 이벤트에 대한 참가자 수
    @GetMapping("/events/{eventId}/count")
    public ResponseEntity<Long> countEventParticipants(@PathVariable Long eventId) {
        long participantCount = participantService.countParticipantsByEventId(eventId);
        return ResponseEntity.ok(participantCount);
    }

    // 특정 참가자의 일정 목록 조회
    @GetMapping("/{profileId}/events")
    public ResponseEntity<List<EventEntity>> getParticipantEvents(@PathVariable Long profileId) {
        List<EventEntity> events = participantService.getEventsForParticipant(profileId);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/list") //All
    public ResponseEntity<List<ParticipantEntity>> getAllParticipants() {
        List<ParticipantEntity> participants = participantService.getParticipants();
        return ResponseEntity.ok(participants);
    }
}




