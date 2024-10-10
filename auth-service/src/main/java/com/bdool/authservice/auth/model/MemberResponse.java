package com.bdool.authservice.auth.model;

import lombok.Data;

@Data
public class MemberResponse {
    private String email;
    private String role;
}
