package com.bdool.chatservice.controller;

import com.bdool.chatservice.model.domain.ChannelModel;
import com.bdool.chatservice.model.entity.ChannelEntity;
import com.bdool.chatservice.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/channel")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    // 채널 생성
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody ChannelModel channel) {
        return ResponseEntity.ok(channelService.save(channel)); // 200 OK
    }

    // 채널 업데이트
    @PutMapping("/{channelId}")
    public ResponseEntity<?> update(@PathVariable UUID channelId, @RequestBody ChannelModel channel) {
        return ResponseEntity.ok(channelService.update(channelId, channel));  // 200 OK
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
    public ResponseEntity<?> findAllByWorkspacesId(@PathVariable int workspaceId) {
        List<ChannelEntity> channels = channelService.findAllByWorkspacesId(workspaceId);
        if (channels.isEmpty()) {
            return ResponseEntity.noContent().build();  // 204 No Content
        }
        return ResponseEntity.ok(channels);  // 200 OK
    }

    // 특정 채널 조회
    @GetMapping("/{channelId}")
    public ResponseEntity<?> findById(@PathVariable UUID channelId) {
        return channelService.findById(channelId)
                .map(ResponseEntity::ok)  // 200 OK if found
                .orElseGet(() -> ResponseEntity.notFound().build());  // 404 Not Found
    }

    // 특정 채널 ID 존재 여부 확인
    @GetMapping("/exists/{channelId}")
    public ResponseEntity<?> existsById(@PathVariable UUID channelId) {
        return ResponseEntity.ok(channelService.existsById(channelId));  // 200 OK
    }

    // 채널 개수 확인
    @GetMapping("/count")
    public ResponseEntity<?> count() {
        return ResponseEntity.ok(channelService.count());  // 200 OK
    }

    // 특정 채널 삭제
    @DeleteMapping("/{channelId}")
    public ResponseEntity<?> deleteById(@PathVariable UUID channelId) {
        channelService.deleteById(channelId);
        return ResponseEntity.noContent().build();  // 204 No Content
    }
}
