package com.bdool.chatbotservice.botresponse.service.impl;

import com.bdool.chatbotservice.botresponse.entity.BotResponse;
import com.bdool.chatbotservice.botresponse.repository.BotResponseRepository;
import com.bdool.chatbotservice.botresponse.service.BotResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BotResponseServiceImpl implements BotResponseService {

    private final BotResponseRepository botResponseRepository;

    @Override
    public Optional<BotResponse> findCachedResponse(String question) {
        return botResponseRepository.findByResponseText(question);
    }

    @Override
    public BotResponse saveResponse(String botResponseText, String modelUsed) {
        BotResponse botResponse = BotResponse
                .builder()
                .responseText(botResponseText)
                .modelUsed(modelUsed)
                .build();
        return botResponseRepository.save(botResponse);
    }
}
