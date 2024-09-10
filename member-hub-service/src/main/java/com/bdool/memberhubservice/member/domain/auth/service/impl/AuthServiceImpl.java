package com.bdool.memberhubservice.member.domain.auth.service.impl;

import com.bdool.memberhubservice.member.domain.auth.service.AuthService;
import com.bdool.memberhubservice.member.domain.member.entity.Member;
import com.bdool.memberhubservice.member.domain.member.service.MemberService;
import com.bdool.memberhubservice.member.global.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;

    @Override
    public boolean login(String email, String token) {
        return token != null && jwtUtil.validateToken(token, email);
    }

    @Override
    public String issueToken(String email) {
        return jwtUtil.generateToken(email);
    }
}
