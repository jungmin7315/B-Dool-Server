package com.bdool.authservice.auth.service.impl;

import com.bdool.authservice.auth.global.util.JwtUtil;
import com.bdool.authservice.auth.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final StringRedisTemplate redisTemplate;

    @Transactional
    @Override
    public String issueToken(String email) {
        String accessToken = jwtUtil.generateAccessToken(email);
        String refreshToken = jwtUtil.generateRefreshToken(email);
        redisTemplate.opsForValue().set(email, refreshToken);
        return accessToken;
    }

    @Override
    public String refreshTokens(String accessToken) {
        String email = jwtUtil.extractEmail(accessToken);
        String storedRefreshToken = redisTemplate.opsForValue().get(email);

        if (storedRefreshToken != null && jwtUtil.validateRefreshToken(storedRefreshToken)) {
            return jwtUtil.generateAccessToken(email);
        }
        throw new IllegalArgumentException("Refresh token invalid or expired");
    }

    @Transactional
    @Override
    public String issueTokensToCookies(String email, HttpServletResponse response) {
        return issueToken(email);
    }

    @Override
    public String accessTokenToCookiesByRefresh(String accessToken, HttpServletResponse response) {
        return refreshTokens(accessToken);
    }

    @Transactional
    @Override
    public void logout(String email, HttpServletResponse response) {
        redisTemplate.delete(email);
    }
}