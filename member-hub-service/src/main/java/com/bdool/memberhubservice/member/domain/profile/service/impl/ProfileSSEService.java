package com.bdool.memberhubservice.member.domain.profile.service.impl;

import com.bdool.memberhubservice.member.domain.profile.entity.model.ProfileNicknameResponse;
import com.bdool.memberhubservice.member.domain.profile.entity.model.ProfileOnlineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProfileSSEService {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter createSseEmitter() {
        SseEmitter emitter = new SseEmitter(0L); // 시간 제한 없음
        Long emitterId = System.currentTimeMillis();
        emitters.put(emitterId, emitter);

        // Emitter가 종료될 때 목록에서 제거
        emitter.onCompletion(() -> emitters.remove(emitterId));
        emitter.onTimeout(() -> emitters.remove(emitterId));
        emitter.onError(e -> emitters.remove(emitterId));

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
            } catch (Exception e) {
                emitter.completeWithError(e);
                emitters.remove(id);
            }
        });
    }
}