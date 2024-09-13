package com.bdool.chatbotservice.ai.service.impl;

import com.bdool.chatbotservice.ai.service.OpenAiChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenAiChatServiceImpl implements OpenAiChatService {

    private final RestTemplate restTemplate;
    @Value("${openai.api-key}")
    private String apiKey;
    private final String apiUrl = "https://api.openai.com/v1/chat/completions";

    @Override
    public String getChatGPTResponse(String question) {
        // HTTP 요청 헤더 설정
        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        // 요청 본문 설정 (Chat 모델에 맞는 형식)
        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-3.5-turbo");

        // messages 배열에 사용자 질문 추가
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");  // 역할은 'user'로 설정
        userMessage.put("content", question);  // 질문 내용 설정

        // messages 배열로 추가
        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(userMessage);
        body.put("messages", messages);  // 필수 파라미터인 messages 추가

        // HTTP 요청 생성
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        // API 호출
        ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, Map.class);

        // 응답에서 텍스트 추출
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
        Map<String, Object> messageResponse = (Map<String, Object>) choices.get(0).get("message");
        return (String) messageResponse.get("content");
    }
}
