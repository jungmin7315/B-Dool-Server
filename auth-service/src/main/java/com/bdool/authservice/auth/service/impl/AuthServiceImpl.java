package com.bdool.authservice.auth.service.impl;

import com.bdool.authservice.auth.global.util.JwtUtil;
import com.bdool.authservice.auth.service.AuthService;
import com.bdool.authservice.auth.service.CustomUserDetailService;
import jakarta.servlet.http.HttpServletResponse;
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

    @Override
    public void issueTokensToCookies(String email, HttpServletResponse response) {
        String accessToken = issueToken(email);
        String refreshToken = jwtUtil.generateRefreshToken(email);
        setTokenCookies(response, accessToken, refreshToken);
    }

    @Override
    public void refreshTokensToCookies(String accessToken, HttpServletResponse response) {
        String newAccessToken = refreshTokens(accessToken);
        if (newAccessToken != null) {
            setTokenCookies(response, newAccessToken, null);
        }
    }

    private void setTokenCookies(HttpServletResponse response, String accessToken, String refreshToken) {

        String accessTokenCookie = String.format("accessToken=%s; Max-Age=%d; Path=/; HttpOnly; Secure; SameSite=Lax",
                accessToken, 60 * 15); // 15분 유효
        response.addHeader("Set-Cookie", accessTokenCookie);

        if (refreshToken != null) {
            String refreshTokenCookie = String.format("refreshToken=%s; Max-Age=%d; Path=/; HttpOnly; Secure; SameSite=Lax",
                    refreshToken, 60 * 60 * 24 * 7);  // 7일 유효
            response.addHeader("Set-Cookie", refreshTokenCookie);
        }
    }
}