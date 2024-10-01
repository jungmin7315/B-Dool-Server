package com.bdool.authservice.auth.service;

import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    String issueToken(String email);

    String refreshTokens(String accessToken);

    void issueTokensToCookies(String email, HttpServletResponse response);

    void refreshTokensToCookies(String accessToken, HttpServletResponse response);
}