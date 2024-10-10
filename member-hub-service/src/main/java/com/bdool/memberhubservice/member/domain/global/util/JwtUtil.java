package com.bdool.memberhubservice.member.domain.global.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    public Map<String, Object> extractEmail(String token) {
        // Bearer 제거
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // 토큰의 페이로드 부분 추출
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid JWT token");
        }

        // Base64 URL 디코딩
        byte[] decodedBytes = Base64.getUrlDecoder().decode(parts[1]);
        String payloadJson = new String(decodedBytes);

        // JSON을 Map으로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(payloadJson, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JWT payload", e);
        }
    }
}