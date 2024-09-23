package com.bdool.authservice.auth.service.impl;

import com.bdool.authservice.auth.global.util.JwtUtil;
import com.bdool.authservice.auth.service.AuthService;
import com.bdool.authservice.auth.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailService customUserDetailService;
    private final StringRedisTemplate redisTemplate;

    @Override
    public String issueToken(String email) {
        var userDetails = customUserDetailService.loadUserByUsername(email);
        if (userDetails != null) {
            String accessToken = jwtUtil.generateAccessToken(email);
            String refreshToken = jwtUtil.generateRefreshToken(email);
            redisTemplate.opsForValue().set(email, refreshToken);
            return accessToken;
        }
        return null;
    }

    @Override
    public String refreshTokens(String accessToken) {
        String email = jwtUtil.extractSubEmail(accessToken);
        String storedRefreshToken = redisTemplate.opsForValue().get(email);
        if (storedRefreshToken != null) {
            return jwtUtil.generateAccessToken(email);
        }
        return null;
    }
}
