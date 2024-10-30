package com.bdool.chatservice.controller;

import com.bdool.chatservice.model.domain.ChannelModel;
import com.bdool.chatservice.model.domain.ParticipantModel;
import com.bdool.chatservice.model.entity.ChannelEntity;
import com.bdool.chatservice.model.entity.ParticipantEntity;
import com.bdool.chatservice.service.ChannelService;
import com.bdool.chatservice.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/channels")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    // 채널 생성
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody ChannelModel channel) {
        return ResponseEntity.ok(channelService.save(channel)); // 200 OK
    }

    // 채널 업데이트 (profileId와 channelId 모두 경로에서 받음)
    @PutMapping("/{channelId}/profile/{profileId}")
    public ResponseEntity<?> update(@PathVariable UUID channelId,
                                    @PathVariable UUID profileId,
                                    @RequestBody ChannelModel channel) {
        // profileId와 channelId를 전달받아 업데이트 처리
        return ResponseEntity.ok(channelService.update(channelId, profileId, channel));  // 200 OK
    }

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        List<ChannelEntity> channels = channelService.findAll();
        if (channels.isEmpty()) {
            return ResponseEntity.noContent().build();  // 204 No Content
        }
        return ResponseEntity.ok(channels);  // 200 OK
    }

    // 워크스페이스에 해당 하는 채널 전체 목록 조회
    @GetMapping("/workspaces/{workspaceId}/channel")
    public ResponseEntity<?> findAllByWorkspacesId(@PathVariable Long workspaceId) {
        List<ChannelEntity> channels = channelService.findAllByWorkspacesId(workspaceId);
        if (channels.isEmpty()) {
            return ResponseEntity.noContent().build();  // 204 No Content
        }
        return ResponseEntity.ok(channels);  // 200 OK
    }

    @GetMapping("/workspaces/{workspaceId}/default-channel")
    public ResponseEntity<?> findAllByDefaultChannel(@PathVariable Long workspaceId) {
        ChannelEntity channels = channelService.findDefaultChannelsByWorkspacesId(workspaceId);
        if (channels == null) {
            return ResponseEntity.noContent().build();  // 204 No Content
        }
        return ResponseEntity.ok(channels);  // 200 OK
    }

    // 특정 채널 조회
    @GetMapping("/{channelId}")
    public ResponseEntity<?> findById(@PathVariable UUID channelId) {
        return ResponseEntity.ok(channelService.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found")));  // 200 OK
    }

    // 채널 삭제
    @DeleteMapping("/{channelId}")
    public ResponseEntity<?> deleteById(@PathVariable UUID channelId) {
        channelService.deleteById(channelId);
        return ResponseEntity.noContent().build();  // 204 No Content
    }
}
