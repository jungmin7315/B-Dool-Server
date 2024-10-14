package com.bdool.notificationservice.notification.domain.notification.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class NotificationSSEService {

    private final Map<Long, List<SseEmitter>> emitters = new ConcurrentHashMap<>();

    public SseEmitter createSseEmitter(Long profileId) {
        SseEmitter emitter = new SseEmitter(0L);

        emitters.computeIfAbsent(profileId, id -> new ArrayList<>()).add(emitter);

        emitter.onCompletion(() -> {
            emitters.get(profileId).remove(emitter);
            System.out.println("SSE connection completed for profileId: " + profileId);
        });

        emitter.onTimeout(() -> {
            emitters.get(profileId).remove(emitter);
            System.out.println("SSE connection timed out for profileId: " + profileId);
            emitter.complete();
        });

        emitter.onError(e -> {
            emitters.get(profileId).remove(emitter);  // 에러 발생 시 emitter 제거
            System.err.println("SSE connection error for profileId: " + profileId + ", error: " + e.getMessage());
            emitter.completeWithError(e);  // 에러 발생 시 완료 처리
        });

        return emitter;  // SseEmitter 반환
    }

    public void sendEventToProfile(Long profileId, String eventName, Object data) {
        List<SseEmitter> emitterList = emitters.get(profileId);
        if (emitterList != null) {
            sendEventToEmitters(eventName, data, emitterList);
        }
    }

    public void sendEventToAllProfiles(String eventName, Object data) {
        emitters.forEach((profileId, emitterList) -> {
            sendEventToEmitters(eventName, data, emitterList);
        });
    }

    private void sendEventToEmitters(String eventName, Object data, List<SseEmitter> emitterList) {
        List<SseEmitter> deadEmitters = new ArrayList<>();
        for (SseEmitter emitter : emitterList) {
            try {
                emitter.send(SseEmitter.event().name(eventName).data(data));
            } catch (IOException e) {
                System.err.println("Error sending event, error: " + e.getMessage());
                deadEmitters.add(emitter);
            }
        }
        emitterList.removeAll(deadEmitters);
    }
}