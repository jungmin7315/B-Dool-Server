package com.bdool.authservice.auth.controller;


import com.bdool.authservice.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<String> generateToken(@RequestParam String email) {
        return ResponseEntity.ok(authService.issueToken(email));
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshTokens(
            @RequestHeader(value = "Authorization", required = false) String accessToken) {
        return ResponseEntity.ok(authService.refreshTokens(accessToken));
    }
}
