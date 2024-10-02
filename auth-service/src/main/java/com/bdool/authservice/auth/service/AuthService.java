package com.bdool.authservice.auth.service;

import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    String issueToken(String email);

    String refreshTokens(String accessToken);

    Boolean issueTokensToCookies(String email, HttpServletResponse response);

    Boolean refreshTokensToCookies(String accessToken, HttpServletResponse response);

    Boolean logout(String email,HttpServletResponse response);
}