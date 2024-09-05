package com.bdool.messageservice.service.impl;

import com.bdool.messageservice.model.entity.MessageEntity;
import com.bdool.messageservice.service.RedisMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisMessageServiceImpl implements RedisMessageService {

    private final RedisTemplate<String, MessageEntity> redisTemplate;

    @Override
    public void cacheMessage(MessageEntity message) {
        redisTemplate.opsForValue().set("message:" + message.getMessageId(), message, 10, TimeUnit.MINUTES);
    }

    @Override
    public MessageEntity getCachedMessage(UUID messageId) {
        return redisTemplate.opsForValue().get("message:" + messageId);
    }
}
