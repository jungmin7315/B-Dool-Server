package com.bdool.messageservice.service;

import com.bdool.messageservice.model.entity.MessageEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface RedisMessageService {
   void cacheMessage(MessageEntity message);
   MessageEntity getCachedMessage(UUID messageId);
}
