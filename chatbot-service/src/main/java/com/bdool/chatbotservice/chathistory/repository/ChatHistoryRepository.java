package com.bdool.chatbotservice.chathistory.repository;

import com.bdool.chatbotservice.chathistory.entity.ChatHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {

    List<ChatHistory> findByProfileId(Long profileId);

    Page<ChatHistory> findByProfileIdOrderByTimestampDesc(Long profileId, Pageable pageable);
}
