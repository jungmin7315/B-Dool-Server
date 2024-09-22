package com.bdool.chatbotservice.botresponse.repository;

import com.bdool.chatbotservice.botresponse.entity.BotResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BotResponseRepository extends JpaRepository<BotResponse, Long> {
    Optional<BotResponse> findByResponseText(String responseText);
}
