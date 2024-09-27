package com.bdool.notificationservice.notification.domain.notification.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class NotificationSSEService {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter createSseEmitter() {
        SseEmitter emitter = new SseEmitter(0L);
        Long emitterId = System.currentTimeMillis();
        emitters.put(emitterId, emitter);

        emitter.onCompletion(() -> {
            emitters.remove(emitterId);
            System.out.println("SSE connection completed: " + emitterId);
        });

        emitter.onTimeout(() -> {
            emitters.remove(emitterId);
            System.out.println("SSE connection timed out: " + emitterId);
            emitter.complete();  // 타임아웃이 발생하면 완료 처리
        });

        emitter.onError(e -> {
            emitters.remove(emitterId);
            System.err.println("SSE connection error: " + emitterId + ", error: " + e.getMessage());
            emitter.completeWithError(e);
        });

        return emitter;  // SseEmitter 반환
    }


    public void sendEventToAllEmitters(String eventName, Object data) {
        emitters.forEach((id, emitter) -> {
            try {
                emitter.send(SseEmitter.event()
                        .name(eventName)
                        .data(data));
            } catch (IOException e) {
                System.err.println("Error sending event: " + e.getMessage());
                emitter.completeWithError(e);
                emitters.remove(id);
            }
        });
    }
}