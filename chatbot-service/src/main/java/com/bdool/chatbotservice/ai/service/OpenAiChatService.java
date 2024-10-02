package com.bdool.chatbotservice.ai.service;

public interface OpenAiChatService {

    String getChatGPTResponse(String question);

    String getChatGPTTranslate(String question);
}
