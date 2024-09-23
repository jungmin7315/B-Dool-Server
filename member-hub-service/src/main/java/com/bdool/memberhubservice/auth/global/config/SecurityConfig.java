package com.bdool.memberhubservice.auth.global.config;

import com.bdool.memberhubservice.auth.handler.OAuth2LoginSuccessHandler;
import com.bdool.memberhubservice.auth.global.filter.JwtAuthenticationFilter;
import com.bdool.memberhubservice.auth.service.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOauth2UserService customOauth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                // 권한 설정
                .authorizeHttpRequests(autz -> autz
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/verification/**").permitAll()
                        .requestMatchers("/api/profiles/**").permitAll()
                        .requestMatchers("/api/sse/**").permitAll()
                        .anyRequest().authenticated()// 나머지 요청은 인증 필요

                )
                .csrf(AbstractHttpConfigurer::disable)
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")  // 로그인 페이지 설정
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOauth2UserService))  // 사용자 정보 서비스 설정
                        .successHandler(oAuth2LoginSuccessHandler)  // 로그인 성공 후 핸들러
                )
                // JWT 필터 추가
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
