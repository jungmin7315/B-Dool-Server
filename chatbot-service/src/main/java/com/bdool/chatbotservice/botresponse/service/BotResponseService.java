package com.bdool.chatbotservice.botresponse.service;

import com.bdool.chatbotservice.botresponse.entity.BotResponse;

import java.util.Optional;

public interface BotResponseService {

    Optional<BotResponse> findCachedResponse(String question);

    BotResponse saveResponse(String botResponseText, String modelUsed);
}
