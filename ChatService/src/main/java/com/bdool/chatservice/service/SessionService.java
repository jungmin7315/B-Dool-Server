package com.bdool.chatservice.service;

import com.bdool.chatservice.model.domain.MessageModel;
import com.bdool.chatservice.model.domain.SessionModel;
import com.bdool.chatservice.model.entity.MessageEntity;
import com.bdool.chatservice.model.entity.SessionEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface SessionService {
    SessionEntity save(SessionModel session);

    SessionEntity update(UUID sessionId, SessionModel session);

    List<SessionEntity> findAll();

    Optional<SessionEntity> findById(UUID sessionId);

    boolean existsById(UUID sessionId);

    long count();

    void deleteById(UUID sessionId);
}
