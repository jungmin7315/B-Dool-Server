package com.bdool.messageservice.service;

import com.bdool.messageservice.model.domain.MessageModel;
import com.bdool.messageservice.model.entity.MessageEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface MessageService {
    MessageEntity save(MessageModel message);
    List<MessageEntity> findAll();
    List<MessageEntity> findAllByChannelId(Long channelId);
    Optional<MessageEntity> findById(UUID messageId);

    boolean existsById(UUID messageId);

    long count();

    void deleteById(UUID messageId);
}