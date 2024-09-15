package com.bdool.memberhubservice.auth.service;

public interface AuthService {

    String issueToken(String email);

    String refreshTokens(String accessToken);
}
