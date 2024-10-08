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
        // 일반 대화 요청 (번역 요청이 아님)
        return getChatGPTResponseInternal(question, false);
    }

    @Override
    public String getChatGPTTranslate(String question) {
        // 번역 요청 (번역 요청임)
        return getChatGPTResponseInternal(question, true);
    }

    private String getChatGPTResponseInternal(String question, boolean isTranslation) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> entity = getMapHttpEntity(question, headers, isTranslation);

        // API 호출
        ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, Map.class);

        // 응답에서 텍스트 추출
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
        Map<String, Object> messageResponse = (Map<String, Object>) choices.get(0).get("message");
        return (String) messageResponse.get("content");
    }

    private static HttpEntity<Map<String, Object>> getMapHttpEntity(String question, HttpHeaders headers, boolean isTranslation) {
        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-3.5-turbo");

        List<Map<String, String>> messages = new ArrayList<>();

        // 번역 요청일 경우 시스템 메시지 추가
        if (isTranslation) {
            Map<String, String> systemMessage = new HashMap<>();
            systemMessage.put("role", "system");
            systemMessage.put("content", "질문에 대한 답변을 하지 말고, 질문을 정확하게 한국어로 번역한 텍스트만 반환해주세요.");
            messages.add(systemMessage);
        } else {
            Map<String, String> systemMessage = new HashMap<>();
            systemMessage.put("role", "system");
            systemMessage.put("content", "질문을 한국어로 답변해주세요.");
            messages.add(systemMessage);
        }

        // 사용자 메시지 (질문)
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", question); // 질문 내용
        messages.add(userMessage);

        body.put("messages", messages);

        return new HttpEntity<>(body, headers);
    }
}