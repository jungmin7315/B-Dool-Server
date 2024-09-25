package com.bdool.chatservice;

import com.bdool.chatservice.model.Enum.SessionType;
import com.bdool.chatservice.model.domain.SessionModel;
import com.bdool.chatservice.service.SessionService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
class ChatServiceApplicationTests {

    SessionService sessionService;

    @Test
    void contextLoads() {
        SessionModel session = new SessionModel();
        session.setStartAt(LocalDateTime.parse("2024-09-06T04:48:31.862Z"));
        session.setEndAt(LocalDateTime.parse("2024-09-06T04:48:31.862Z"));
        session.setProfileId(1L);
        session.setSessionType(SessionType.valueOf("VIDEO_CONFERENCE"));
        session.setChannelMemberId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"));
        sessionService.save(session);
    }

}
