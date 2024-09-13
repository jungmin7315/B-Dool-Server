package com.bdool.chatbotservice.chathistory.service;

import com.bdool.chatbotservice.chathistory.entity.ChatHistory;

import java.util.List;

public interface ChatHistoryService {

    String processChat(Long workspaceId, Long profileId, String question);

    List<ChatHistory> getChatHistory(Long workspaceId, Long profileId);
}
