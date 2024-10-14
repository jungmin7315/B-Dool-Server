package com.bdool.memberhubservice.member.domain.profile.service.impl;

import com.bdool.memberhubservice.member.domain.profile.entity.model.sse.ProfileNicknameResponse;
import com.bdool.memberhubservice.member.domain.profile.entity.model.sse.ProfileOnlineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
public class ProfileSSEService {

    // SseEmitter를 관리하는 ConcurrentHashMap (Thread-safe)
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    // 클라이언트가 SSE 구독 요청을 하면 호출
    public SseEmitter createSseEmitter() {
        SseEmitter emitter = new SseEmitter(0L);  // 타임아웃을 무한대로 설정
        Long emitterId = System.currentTimeMillis();
        emitters.put(emitterId, emitter);  // emitters에 새 구독자 추가

        // SSE 연결이 정상적으로 종료될 때
        emitter.onCompletion(() -> {
            emitters.remove(emitterId);  // 완료되면 emitters에서 제거
            System.out.println("SSE connection completed: " + emitterId);
        });

        // SSE 연결이 타임아웃될 때
        emitter.onTimeout(() -> {
            emitters.remove(emitterId);  // 타임아웃 발생 시 emitters에서 제거
            System.out.println("SSE connection timed out: " + emitterId);
            emitter.complete();  // 타임아웃이 발생하면 완료 처리
        });

        // SSE 연결 중 에러가 발생할 때
        emitter.onError(e -> {
            emitters.remove(emitterId);  // 에러 발생 시 emitters에서 제거
            System.err.println("SSE connection error: " + emitterId + ", error: " + e.getMessage());
            emitter.completeWithError(e);  // 에러가 발생하면 완료 처리
        });

        return emitter;  // SseEmitter 반환
    }

    // 닉네임 변경 이벤트를 모든 구독자에게 전송
    public void notifyNicknameChange(ProfileNicknameResponse profileNicknameResponse) {
        sendEventToAllEmitters("nickname-change", profileNicknameResponse);
    }

    // 온라인 상태 변경 이벤트를 모든 구독자에게 전송
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
                // Broken pipe 또는 기타 IO 예외가 발생한 경우
                System.err.println("Error sending event: " + e.getMessage());
                emitter.completeWithError(e);  // 에러 발생 시 해당 Emitter 종료
                emitters.remove(id);  // Emitter를 emitters 맵에서 제거
            }
        });
    }
}