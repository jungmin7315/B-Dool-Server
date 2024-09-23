package com.bdool.memberhubservice.member.domain.profile.service.impl;

import com.bdool.memberhubservice.member.domain.profile.entity.model.ProfileNicknameResponse;
import com.bdool.memberhubservice.member.domain.profile.entity.model.ProfileOnlineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
public class ProfileSSEService {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter createSseEmitter() {
        SseEmitter emitter = new SseEmitter(300_000L);  // 5분 타임아웃
        Long emitterId = System.currentTimeMillis();
        emitters.put(emitterId, emitter);

        // SSE 연결 완료 시
        emitter.onCompletion(() -> {
            emitters.remove(emitterId);
            System.out.println("SSE connection completed: " + emitterId);
        });

        // SSE 연결 타임아웃 시
        emitter.onTimeout(() -> {
            emitters.remove(emitterId);
            System.out.println("SSE connection timed out: " + emitterId);
            emitter.complete();  // 타임아웃이 발생했을 때는 완료 처리
        });

        // SSE 에러 발생 시
        emitter.onError(e -> {
            emitters.remove(emitterId);
            System.err.println("SSE connection error: " + emitterId + ", error: " + e.getMessage());
            emitter.completeWithError(e);  // 에러 발생 시 완료 처리
        });

        return emitter;
    }

    public void notifyNicknameChange(ProfileNicknameResponse profileNicknameResponse) {
        sendEventToAllEmitters("nickname-change", profileNicknameResponse);
    }

    public void notifyOnlineChange(ProfileOnlineResponse profileOnlineResponse) {
        sendEventToAllEmitters("online-status-change", profileOnlineResponse);
    }

    private void sendEventToAllEmitters(String eventName, Object data) {
        emitters.forEach((id, emitter) -> {
            try {
                emitter.send(SseEmitter.event()
                        .name(eventName)
                        .data(data));
            } catch (IOException e) {
                emitter.completeWithError(e);  // 에러 발생 시 연결을 종료
                emitters.remove(id);
                System.err.println("Error sending event: " + e.getMessage());
            }
        });
    }
}