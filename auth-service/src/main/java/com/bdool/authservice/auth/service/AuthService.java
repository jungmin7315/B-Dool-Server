package com.bdool.authservice.auth.service;

import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    String issueToken(String email);

    String refreshTokens(String accessToken);

    Boolean issueTokensToCookies(String email, HttpServletResponse response);

    Boolean accessTokenToCookiesByRefresh(String accessToken, HttpServletResponse response);

    void logout(String email,HttpServletResponse response);
}