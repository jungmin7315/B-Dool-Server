package com.bdool.authservice.auth.service;

public interface AuthService {

    String issueToken(String email);

    String refreshTokens(String accessToken);
}
