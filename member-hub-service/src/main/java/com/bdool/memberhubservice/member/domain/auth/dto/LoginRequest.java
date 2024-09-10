package com.bdool.memberhubservice.member.domain.auth.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String token;
    private String email;
}
