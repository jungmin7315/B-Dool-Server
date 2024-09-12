package com.bdool.chatbotservice.chathistory.controller;

import com.bdool.chatbotservice.chathistory.entity.ChatHistory;
import com.bdool.chatbotservice.chathistory.service.ChatHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chatbot")
@RequiredArgsConstructor
public class ChatHistoryController {

    private final ChatHistoryService chatHistoryService;

    @PostMapping("/ask")
    public ResponseEntity<String> askQuestion(
            @RequestParam Long workspaceId, // 프론트에서 워크스페이스 ID 전달
            @RequestParam Long profileId,   // 프론트에서 사용자 프로필 ID 전달
            @RequestBody String question) { // 사용자가 보낸 질문 본문

        // 서비스에서 질문을 처리하고 AI 응답 반환
        return ResponseEntity.ok(chatHistoryService.processChat(workspaceId, profileId, question)); // 응답 반환
    }

    // 사용자의 대화 기록을 조회하는 엔드포인트
    @GetMapping("/history")
    public ResponseEntity<List<ChatHistory>> getChatHistory(
            @RequestParam Long workspaceId, // 프론트에서 워크스페이스 ID 전달
            @RequestParam Long profileId) { // 프론트에서 사용자 프로필 ID 전달
        // 해당 사용자와 워크스페이스의 대화 기록 조회
        return ResponseEntity.ok(chatHistoryService.getChatHistory(workspaceId, profileId)); // 대화 기록 리스트 반환
    }
}
