package com.bdool.chatbotservice.chathistory.service.impl;

import com.bdool.chatbotservice.ai.service.OpenAiChatService;
import com.bdool.chatbotservice.chathistory.entity.ChatHistory;
import com.bdool.chatbotservice.chathistory.repository.ChatHistoryRepository;
import com.bdool.chatbotservice.chathistory.service.ChatHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ChatHistoryServiceImpl implements ChatHistoryService {

    private final ChatHistoryRepository chatHistoryRepository;
    private final OpenAiChatService openAiChatService;
    private final RedisTemplate<String, String> redisTemplate;
    private static final String REDIS_TRANSLATE_PREFIX = "translate:";

    @Override
    public String processChat(Long profileId, Map<String, String> requestBody) {
        String question = requestBody.get("question");

        List<ChatHistory> previousChats = chatHistoryRepository.findByProfileId(profileId);
        List<Map<String, String>> previousMessages = new ArrayList<>();

        // 이전 대화를 OpenAI API 메시지 형식으로 변환
        for (ChatHistory chat : previousChats) {
            Map<String, String> userMessage = new HashMap<>();
            userMessage.put("role", "user");
            userMessage.put("content", chat.getQuestion());
            previousMessages.add(userMessage);

            Map<String, String> botMessage = new HashMap<>();
            botMessage.put("role", "assistant");
            botMessage.put("content", chat.getBotResponse());
            previousMessages.add(botMessage);
        }

        // OpenAI API 호출 (이전 대화 + 새로운 질문)
        String aiResponse = openAiChatService.getChatGPTResponse(previousMessages, question);

        // 새 응답 저장

        saveChatHistory(profileId, question, aiResponse);

        return aiResponse;
    }


    private void saveChatHistory(Long profileId, String question, String cachedResponse) {
        ChatHistory chatHistory = ChatHistory.builder()
                .profileId(profileId)
                .question(question)
                .botResponse(cachedResponse)
                .build();
        chatHistoryRepository.save(chatHistory);
    }

    @Override
    public String processTranslate(Map<String, String> requestBody) {
        String question = requestBody.get("question");
        String cacheKey = REDIS_TRANSLATE_PREFIX + question;


        String cachedTranslation = redisTemplate.opsForValue().get(cacheKey);
        if (cachedTranslation != null) {
            return cachedTranslation;
        } else {
            String translatedText = openAiChatService.getChatGPTTranslate(question);
            redisTemplate.opsForValue().set(cacheKey, translatedText, 1, TimeUnit.HOURS);
            return translatedText;
        }
    }

    @Override
    public List<ChatHistory> getChatHistory(Long profileId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        return chatHistoryRepository.findByProfileIdOrderByTimestampDesc(profileId, pageable).getContent();
    }
}