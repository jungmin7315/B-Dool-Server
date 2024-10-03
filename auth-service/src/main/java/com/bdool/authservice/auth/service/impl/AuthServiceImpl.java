package com.bdool.authservice.auth.service.impl;

import com.bdool.authservice.auth.global.util.JwtUtil;
import com.bdool.authservice.auth.service.AuthService;
import jakarta.servlet.http.Cookie;
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
    public Boolean issueTokensToCookies(String email, HttpServletResponse response) {
        String accessToken = issueToken(email);
        setTokenCookies(response, accessToken);
        return true;
    }

    @Override
    public Boolean accessTokenToCookiesByRefresh(String accessToken, HttpServletResponse response) {
        String newAccessToken = refreshTokens(accessToken);
        setTokenCookies(response, newAccessToken);
        return true;
    }

    @Transactional
    @Override
    public void logout(String email, HttpServletResponse response) {
        redisTemplate.delete(email);
        clearCookies(response);
    }

    private void setTokenCookies(HttpServletResponse response, String accessToken) {
        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true);  // 클라이언트 스크립트에서 접근 불가
        accessTokenCookie.setSecure(false);
        accessTokenCookie.setPath("/");       // 모든 경로에서 쿠키 사용 가능
        accessTokenCookie.setMaxAge(60 * 30); // 쿠키 유효 시간 30분
        response.addCookie(accessTokenCookie);
    }

    private void clearCookies(HttpServletResponse response) {
        Cookie clearCookie = new Cookie("accessToken", null);
        clearCookie.setMaxAge(0);
        clearCookie.setPath("/");
        response.addCookie(clearCookie);
    }
}