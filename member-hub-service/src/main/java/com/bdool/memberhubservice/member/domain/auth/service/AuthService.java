package com.bdool.memberhubservice.member.domain.auth.service;

public interface AuthService {
    boolean login(String email, String  token);

    String issueToken(String email);
}
