package com.bdool.chatbotservice.chathistory.service;

import com.bdool.chatbotservice.chathistory.entity.ChatHistory;

import java.util.List;
import java.util.Map;

public interface ChatHistoryService {

    String processChat(Long profileId, Map<String, String> requestBody);

    String processTranslate(Map<String, String> requestBody);

    List<ChatHistory> getChatHistory(Long profileId, int page, int size);
}
