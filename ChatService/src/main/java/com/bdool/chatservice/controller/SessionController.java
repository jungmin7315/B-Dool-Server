package com.bdool.chatservice.controller;

import com.bdool.chatservice.model.domain.SessionModel;
import com.bdool.chatservice.model.entity.SessionEntity;
import com.bdool.chatservice.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/session")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody SessionModel session) {
        return ResponseEntity.ok(sessionService.save(session));
    }

    @PutMapping("/{sessionId}")
    public ResponseEntity<?> update(@PathVariable UUID sessionId,@RequestBody SessionModel session) {
        return ResponseEntity.ok(sessionService.update(sessionId, session));
    }

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        List<SessionEntity> sessions = sessionService.findAll();
        if(sessions.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<?> findById(@PathVariable UUID sessionId) {
        return ResponseEntity.ok(sessionService.findById(sessionId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build())
        );
    }

    @GetMapping("/exists/{sessionId}")
    public ResponseEntity<?> existsById(@PathVariable UUID sessionId) {
        return ResponseEntity.ok(sessionService.existsById(sessionId));
    }

    @GetMapping("/count")
    public ResponseEntity<?> count() {
        return ResponseEntity.ok(sessionService.count());
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<?> deleteById(@PathVariable UUID sessionId) {
        sessionService.deleteById(sessionId);
        return ResponseEntity.noContent().build();
    }
}
