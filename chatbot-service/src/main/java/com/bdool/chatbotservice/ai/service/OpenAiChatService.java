package com.bdool.chatbotservice.ai.service;

import java.util.List;
import java.util.Map;

public interface OpenAiChatService {

    String getChatGPTResponse(List<Map<String, String>> previousMessages, String question);

    String getChatGPTTranslate(String question);
}
