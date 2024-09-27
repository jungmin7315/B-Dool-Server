package com.bdool.notificationservice.notification.domain.notification.controller;

import com.bdool.notificationservice.notification.domain.notification.service.impl.NotificationSSEService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/notification/sse")
@RequiredArgsConstructor
@CrossOrigin
public class NotificationSSEController {

    private final NotificationSSEService sseService;

    @GetMapping("/subscribe")
    public SseEmitter subscribe(@RequestParam("profileId") Long profileId) {
        return sseService.createSseEmitter(profileId);
    }
}