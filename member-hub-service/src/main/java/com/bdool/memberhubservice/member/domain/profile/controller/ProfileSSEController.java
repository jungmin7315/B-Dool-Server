package com.bdool.memberhubservice.member.domain.profile.controller;

import com.bdool.memberhubservice.member.domain.profile.service.impl.ProfileSSEService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/profile/api/sse")
@RequiredArgsConstructor
@CrossOrigin
public class ProfileSSEController {

    private final ProfileSSEService sseService;

    @GetMapping("/subscribe")
    public SseEmitter subscribe() {
        return sseService.createSseEmitter();
    }
}