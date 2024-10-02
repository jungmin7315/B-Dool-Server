package com.bdool.authservice.auth.global.filter;

import com.bdool.authservice.auth.global.util.JwtUtil;
import com.bdool.authservice.auth.service.CustomUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailService customUserDetailService;
    private final StringRedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String accessToken = null;
        String email = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            accessToken = authorizationHeader.substring(7);  // "Bearer " 이후의 토큰 부분 추출
            email = jwtUtil.extractEmail(accessToken);  // JWT에서 사용자명 추출
        }
        // JWT가 유효하고, 인증 정보가 없을 경우
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 사용자 정보를 로드하고, JWT 검증
            if (jwtUtil.validateAccessToken(accessToken, email)) {
                var userDetails = customUserDetailService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 인증 정보 설정
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                // Access Token이 유효하지 않거나 만료된 경우 Refresh Token 검증
                String refreshToken = redisTemplate.opsForValue().get(email);
                if (refreshToken != null && jwtUtil.validateRefreshToken(refreshToken)) {
                    var userDetails = customUserDetailService.loadUserByUsername(email);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
