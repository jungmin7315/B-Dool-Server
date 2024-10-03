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
    public ResponseEntity<Boolean> generateToken(@RequestParam String email, HttpServletResponse response) {

        return ResponseEntity.ok(authService.issueTokensToCookies(email, response));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Boolean> refreshTokens(@RequestHeader(
            value = "Authorization", required = false) String accessToken, HttpServletResponse response) {
        if (accessToken == null || !accessToken.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Access token is missing or invalid");
        }
        return ResponseEntity.ok(authService.accessTokenToCookiesByRefresh(accessToken, response));
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestParam String email, HttpServletResponse response) {
        authService.logout(email, response);
        return ResponseEntity.noContent().build();
    }
}