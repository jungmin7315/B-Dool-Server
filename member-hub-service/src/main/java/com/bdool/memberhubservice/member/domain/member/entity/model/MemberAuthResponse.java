package com.bdool.memberhubservice.member.domain.member.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberAuthResponse {
    private String email;
    private String role;
}
