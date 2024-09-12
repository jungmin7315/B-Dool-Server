package com.bdool.chatbotservice.chathistory.repository;

import com.bdool.chatbotservice.chathistory.entity.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {
    List<ChatHistory> findByWorkspaceIdAndProfileId(Long workspaceId, Long profileId);
}
