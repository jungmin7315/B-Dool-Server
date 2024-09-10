package com.bdool.memberhubservice.member.domain.auth.controller;

import com.bdool.memberhubservice.member.domain.auth.dto.LoginResponse;
import com.bdool.memberhubservice.member.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestParam String email,
                                               @RequestHeader(value = "Authorization", required = false) String token) {
        return ResponseEntity.ok(authService.login(email, token));
    }

    @PostMapping("/token")
    public ResponseEntity<String> generateToken(@RequestParam String email) {
        return ResponseEntity.ok(authService.issueToken(email));
    }
}
