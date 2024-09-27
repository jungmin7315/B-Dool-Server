package com.bdool.chatservice.service.impl;

import com.bdool.chatservice.exception.SessionNotFoundException;
import com.bdool.chatservice.model.domain.SessionModel;
import com.bdool.chatservice.model.entity.SessionEntity;
import com.bdool.chatservice.model.repository.SessionRepository;
import com.bdool.chatservice.service.SessionService;
import com.bdool.chatservice.util.UUIDUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    @Override
    public SessionEntity save(SessionModel session) {
        UUID sessionId = UUIDUtil.getOrCreateUUID(session.getSessionId());

        return sessionRepository.save(SessionEntity.builder()
                .sessionId(sessionId)
                .sessionType(session.getSessionType())
                .startAt(session.getStartAt())
                .endAt(session.getEndAt())
                .profileId(session.getProfileId())
                .channelMemberId(session.getChannelMemberId())
                .build());
    }

    @Override
    public SessionEntity update(UUID sessionId, SessionModel session) {
        return sessionRepository.findById(sessionId)
                .map(existingSession -> {
                    existingSession.setStartAt(session.getStartAt());
                    existingSession.setEndAt(session.getEndAt());
                    existingSession.setProfileId(session.getProfileId());
                    existingSession.setChannelMemberId(session.getChannelMemberId());
                    return sessionRepository.save(existingSession);
                }).orElseThrow(() -> new SessionNotFoundException("Session not found with ID: " + sessionId));
    }

    @Override
    public List<SessionEntity> findAll() {
        return sessionRepository.findAll();
    }

    @Override
    public Optional<SessionEntity> findById(UUID sessionId) {
        return sessionRepository.findById(sessionId);
    }

    @Override
    public boolean existsById(UUID sessionId) {
        return sessionRepository.existsById(sessionId);
    }

    @Override
    public long count() {
        return sessionRepository.count();
    }

    @Override
    public void deleteById(UUID sessionId) {
        sessionRepository.deleteById(sessionId);
    }
}
