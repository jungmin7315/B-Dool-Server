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
    public String getChatGPTResponse(List<Map<String, String>> previousMessages, String newQuestion) {
        // 일반 대화 요청 (번역이 아님)
        return getChatGPTResponseInternal(previousMessages, newQuestion, false);
    }

    @Override
    public String getChatGPTTranslate(String question) {
        // 번역 요청 (번역임, 이전 대화 내역 없음)
        return getChatGPTResponseInternal(new ArrayList<>(), question, true);
    }

    private String getChatGPTResponseInternal(List<Map<String, String>> previousMessages, String question, boolean isTranslation) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        // 공통된 HttpEntity 생성
        HttpEntity<Map<String, Object>> entity = createHttpEntity(previousMessages, question, headers, isTranslation);

        // API 호출
        ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, Map.class);

        // 응답에서 텍스트 추출
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
        Map<String, Object> messageResponse = (Map<String, Object>) choices.get(0).get("message");
        return (String) messageResponse.get("content");
    }

    // 공통된 HttpEntity 생성 로직
    private HttpEntity<Map<String, Object>> createHttpEntity(List<Map<String, String>> previousMessages, String question, HttpHeaders headers, boolean isTranslation) {
        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-3.5-turbo");

        // 이전 대화 내역을 포함한 메시지 리스트
        List<Map<String, String>> messages = new ArrayList<>(previousMessages);

        // 번역 요청일 경우 시스템 메시지 추가
        if (isTranslation) {
            Map<String, String> systemMessage = new HashMap<>();
            systemMessage.put("role", "system");
            systemMessage.put("content",
                    "질문에 대한 답변을 하지 말고, 사람 이름이나 고유 명사, 대명사는 번역하지 않고 그대로 유지하면서 질문을 정확하게 한국어로 번역하세요. " +
                            "가능한 한 원문의 어조와 의미를 유지하며, 자연스럽고 정확한 번역을 반환해주세요.");
            messages.add(systemMessage);
        } else {
            // 일반 대화일 경우 시스템 메시지 추가
            Map<String, String> systemMessage = new HashMap<>();
            systemMessage.put("role", "system");
            systemMessage.put("content", "질문을 한국어로 답변해주세요.");
            messages.add(systemMessage);
        }

        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", question); // 질문 내용
        messages.add(userMessage);

        body.put("messages", messages);  // 이전 메시지 + 새로운 메시지
        return new HttpEntity<>(body, headers);
    }
}