package com.bdool.authservice.auth.service;

import com.bdool.authservice.auth.model.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final WebClient webClient;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberResponse member = webClient.get()
                .uri("/members/email/{email}", email)
                .retrieve()
                .bodyToMono(MemberResponse.class)
                .block();  // 동기식으로 결과 기다림

        if (member == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(member.getRole());

        return new User(member.getEmail(), "", authorities);
    }
}