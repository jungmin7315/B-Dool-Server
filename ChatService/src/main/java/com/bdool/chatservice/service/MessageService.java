package com.bdool.chatservice.service;

import com.bdool.chatservice.model.domain.MessageModel;
import com.bdool.chatservice.model.entity.MessageEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface MessageService {
    MessageEntity save(MessageModel message);

    MessageEntity update(UUID messageId, MessageModel message);

    List<MessageEntity> findAll();

    List<MessageEntity> findByChannelId(UUID channelId);

    MessageEntity findById(UUID messageId);

    Boolean existsById(UUID messageId);

    Long count();

    void deleteById(UUID messageId);
}