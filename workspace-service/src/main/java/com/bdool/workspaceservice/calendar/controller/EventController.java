package com.bdool.bdool.calendar.controller;

import com.bdool.bdool.calendar.model.domain.EventRequest;
import com.bdool.bdool.calendar.model.entity.EventEntity;
import com.bdool.bdool.calendar.service.EventService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventEntity> createEvent(@RequestBody EventRequest request) {
        EventEntity createdEvent = eventService.createEvent(request);

        return ResponseEntity.ok(createdEvent);
    }

    // 이벤트 ID로 조회
    @GetMapping("/{eventId}")
    public ResponseEntity<EventEntity> getEventById(@PathVariable Long eventId) {
        Optional<EventEntity> event = eventService.getEventById(eventId);
        if (event.isPresent()) {
            return ResponseEntity.ok(event.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{eventId}")
    public ResponseEntity<EventEntity> updateEvent(@PathVariable Long eventId, @RequestBody EventRequest request) {
        try {
            EventEntity updatedEvent = eventService.updateEvent(eventId, request);
            return ResponseEntity.ok(updatedEvent);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) {
        if (eventService.existsEvent(eventId)) {
            eventService.deleteEvent(eventId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/{eventId}/exists")
    public ResponseEntity<Boolean> existsEvent(@PathVariable Long eventId) {
        boolean exists = eventService.existsEvent(eventId);
        return ResponseEntity.ok(exists);
    }

    // 전체 이벤트 수 조회
    @GetMapping("/count")
    public ResponseEntity<Long> countEvent() {
        long count = eventService.countEvent();
        return ResponseEntity.ok(count);
    }
}
