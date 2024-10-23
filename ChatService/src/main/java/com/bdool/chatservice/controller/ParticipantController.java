package com.bdool.chatservice.controller;

import com.bdool.chatservice.model.domain.ParticipantModel;
import com.bdool.chatservice.model.entity.ParticipantEntity;
import com.bdool.chatservice.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/participant")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody ParticipantModel participant) {
        return ResponseEntity.ok(participantService.save(participant));
    }

    @PutMapping("/{participantId}")
    public ResponseEntity<?> update(@PathVariable UUID participantId, @RequestBody ParticipantModel member) {
        return ResponseEntity.ok(participantService.update(participantId, member));
    }

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        List<ParticipantEntity> participants = participantService.findAll();
        if (participants.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(participants);
    }

    @GetMapping("/{participantId}")
    public ResponseEntity<?> findById(@PathVariable UUID participantId) {
        return ResponseEntity.ok(participantService.findById(participantId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build()));
    }

    @GetMapping("/{channelId}/channel")
    public ResponseEntity<?> findByChannelId(@PathVariable UUID channelId){
        return ResponseEntity.ok(participantService.findByChannelId(channelId));
    }

    @GetMapping("/exists/{participantId}")
    public ResponseEntity<?> existsById(@PathVariable UUID participantId) {
        return ResponseEntity.ok(participantService.existsById(participantId));
    }

    @GetMapping("/count")
    public ResponseEntity<?> count() {
        return ResponseEntity.ok(participantService.count());
    }

    @DeleteMapping("/{participantId}")
    public ResponseEntity<?> deleteById(@PathVariable UUID participantId) {
        participantService.deleteById(participantId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{profileId}/online")
    public ResponseEntity<Void> updateOnlineStatus(@PathVariable Long profileId, @RequestParam Boolean isOnline) {
        participantService.updateOnline(profileId,isOnline);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{profileId}/nickname")
    public ResponseEntity<Void> updateNickname(@PathVariable Long profileId, @RequestParam String nickname) {
        return ResponseEntity.noContent().build();
    }
}
