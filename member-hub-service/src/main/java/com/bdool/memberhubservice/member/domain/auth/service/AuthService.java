package com.bdool.memberhubservice.member.domain.auth.service;

import com.bdool.memberhubservice.member.domain.auth.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(String email, String token);

    String issueToken(String email);
}
