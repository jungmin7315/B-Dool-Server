package com.bdool.authservice.auth.global.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final StringRedisTemplate redisTemplate;
    private final String SECRET_KEY = "9e4898f0e111202c594d9b06197d62fe3b812fa629fea03f00ecfc0d968b5bcbc8d82fef1bec013feb13dee1bf5b1e8f4b4d04d154b567ba57e0a51109247745";

    // Access Token 생성
    public String generateAccessToken(String email) {
//            return createToken(email, 1);  // 15분 유효
            return createToken(email, 1000 * 60 * 15);  // 15분 유효
    }

    // Refresh Token 생성
    public String generateRefreshToken(String email) {
        return createToken(email, 1000 * 60 * 60 * 24 * 7);  // 1주일 유효
    }

    // JWT 생성
    private String createToken(String email, long expirationTime) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // JWT 에서 사용자 Email 추출
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractSubEmail(String accessToken) {
        String subToken = accessToken.substring(7);
        return extractClaim(subToken, Claims::getSubject);
    }

    // JWT 에서 특정 클레임 추출
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // JWT 에서 모든 클레임 추출
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    // JWT 만료 여부 확인
    public Boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        Date now = new Date();
        System.out.println("Token Expiration: " + expiration);
        System.out.println("Current Time: " + now);
        return expiration.before(now);
    }

    // JWT 의 만료 시간 추출
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Access 토큰 유효성 검증
    public Boolean validateAccessToken(String accessToken, String email) {
        try {
            final String extractEmail = extractEmail(accessToken);
            return (extractEmail.equals(email) && !isTokenExpired(accessToken));
        } catch (Exception e) {
            System.err.println("Token validation failed: " + e.getMessage());
            return false;
        }
    }

    // Refresh 토큰 검증
    public Boolean validateRefreshToken(String refreshToken) {
        try {
            return isTokenExpired(refreshToken);
        } catch (Exception e) {
            return false;
        }
    }
}