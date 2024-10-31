package com.bdool.chatservice.sse;

import com.bdool.chatservice.sse.model.ParticipantNicknameResponse;
import com.bdool.chatservice.sse.model.ParticipantOnlineResponse;
import com.bdool.chatservice.sse.model.ParticipantPorfileUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class ParticipantSSEService {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();


    public SseEmitter createSseEmitter() {
        SseEmitter emitter = new SseEmitter(0L);  // 타임아웃을 무한대로 설정
        Long emitterId = System.currentTimeMillis();
        emitters.put(emitterId, emitter);

        // SSE 연결이 정상적으로 종료될 때
        emitter.onCompletion(() -> {
            emitters.remove(emitterId);
            System.out.println("SSE connection completed: " + emitterId);
        });

        // SSE 연결이 타임아웃될 때
        emitter.onTimeout(() -> {
            emitters.remove(emitterId);
            System.out.println("SSE connection timed out: " + emitterId);
            emitter.complete();  // 타임아웃이 발생하면 완료 처리
        });

        // SSE 연결 중 에러가 발생할 때
        emitter.onError(e -> {
            emitters.remove(emitterId);
            System.err.println("SSE connection error: " + emitterId + ", error: " + e.getMessage());
            emitter.completeWithError(e);  // 에러가 발생하면 완료 처리
        });

        return emitter;  // SseEmitter 반환
    }

    // 닉네임 변경 이벤트를 모든 구독자에게 전송
    public void notifyNicknameChange(ParticipantNicknameResponse participantNicknameResponse) {
        sendEventToAllEmitters("nickname-change", participantNicknameResponse);
    }

    // 온라인 상태 변경 이벤트를 모든 구독자에게 전송
    public void notifyOnlineChange(ParticipantOnlineResponse participantOnlineResponse) {
        sendEventToAllEmitters("online-status-change", participantOnlineResponse);
    }

    public void notifyProfileUrlChange(ParticipantPorfileUrlResponse participantPorfileUrlResponse) {
        sendEventToAllEmitters("profileUrl-change", participantPorfileUrlResponse);
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
