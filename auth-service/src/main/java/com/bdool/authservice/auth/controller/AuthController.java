package com.bdool.authservice.auth.controller;


import com.bdool.authservice.auth.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<Void> generateToken(@RequestParam String email, HttpServletResponse response) {
        authService.issueTokensToCookies(email, response);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refreshTokens(@RequestHeader(value = "Authorization", required = false) String accessToken, HttpServletResponse response) {
        authService.refreshTokensToCookies(accessToken, response);
        return ResponseEntity.ok().build();
    }
}