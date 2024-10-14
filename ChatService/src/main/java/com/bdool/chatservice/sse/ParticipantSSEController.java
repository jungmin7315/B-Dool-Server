package com.bdool.chatservice.sse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/participant/api/sse")
@RequiredArgsConstructor
@CrossOrigin
public class ParticipantSSEController {

    private final ParticipantSSEService sseService;

    @GetMapping("/subscribe")
    public SseEmitter subscribe() {
        return sseService.createSseEmitter();
    }
}
