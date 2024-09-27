package com.bdool.notificationservice.notification.domain.notification.controller;

import com.bdool.notificationservice.notification.domain.notification.service.impl.NotificationSSEService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/notification/sse")
@RequiredArgsConstructor
public class NotificationSSEController {

    private final NotificationSSEService sseService;

    @GetMapping("/subscribe")
    public SseEmitter subscribe() {
        return sseService.createSseEmitter();
    }
}