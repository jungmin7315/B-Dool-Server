package com.bdool.memberhubservice.member.domain.auth.service;

import com.bdool.memberhubservice.member.domain.auth.dto.LoginResponse;

public interface AuthService {
    Boolean login(String token);

    String issueToken(String email);
}
