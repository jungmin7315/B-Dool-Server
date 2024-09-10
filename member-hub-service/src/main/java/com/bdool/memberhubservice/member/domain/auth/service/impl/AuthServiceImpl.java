package com.bdool.memberhubservice.member.domain.auth.service.impl;

import com.bdool.memberhubservice.member.domain.auth.dto.LoginResponse;
import com.bdool.memberhubservice.member.domain.auth.service.AuthService;
import com.bdool.memberhubservice.global.util.JwtUtil;
import com.bdool.memberhubservice.member.domain.auth.service.CustomUserDetailService;
import com.bdool.memberhubservice.member.domain.member.entity.Member;
import com.bdool.memberhubservice.member.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailService customUserDetailService;

    @Override
    public LoginResponse login(String email, String token) {
        if (token != null && email.equals(jwtUtil.extractEmail(token))) {
            return new LoginResponse(true, jwtUtil.generateToken(email));
        }
        return new LoginResponse(false, null);
    }

    @Override
    public String issueToken(String email) {
        var userDetails = customUserDetailService.loadUserByUsername(email);
        if (userDetails != null) {
            return jwtUtil.generateToken(email);
        }
        throw new IllegalArgumentException("Invalid user email.");
    }
}
