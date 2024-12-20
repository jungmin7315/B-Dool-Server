package com.bdool.chatbotservice.chathistory.controller;

import com.bdool.chatbotservice.chathistory.entity.ChatHistory;
import com.bdool.chatbotservice.chathistory.service.ChatHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chatbot")
@RequiredArgsConstructor
public class ChatHistoryController {

    private final ChatHistoryService chatHistoryService;

    @PostMapping("/ask")
    public ResponseEntity<String> askQuestion(
            @RequestParam Long profileId,   // 프론트에서 사용자 프로필 ID 전달
            @RequestBody Map<String, String> requestBody) { // 사용자가 보낸 질문 본문
        // 서비스에서 질문을 처리하고 AI 응답 반환
        return ResponseEntity.ok(chatHistoryService.processChat(profileId, requestBody)); // 응답 반환
    }

    @PostMapping("/translate")
    public ResponseEntity<String> askTranslate(
            @RequestBody Map<String, String> question) {
        // 서비스에서 질문을 처리하고 AI 응답 반환
        return ResponseEntity.ok(chatHistoryService.processTranslate(question)); // 응답 반환
    }

    // 사용자의 대화 기록을 조회하는 엔드포인트
    @GetMapping("/history")
    public ResponseEntity<List<ChatHistory>> getChatHistory(
            @RequestParam Long profileId,
            @RequestParam int page,
            @RequestParam int size) { // 프론트에서 사용자 프로필 ID 전달
        // 해당 사용자와 워크스페이스의 대화 기록 조회
        return ResponseEntity.ok(chatHistoryService.getChatHistory(profileId, page, size));
    }
}
