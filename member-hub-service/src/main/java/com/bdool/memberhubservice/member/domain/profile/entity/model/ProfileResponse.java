package com.bdool.memberhubservice.member.domain.profile.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {
    private Long id;
    private String nickname;
    private Boolean isOnline;
    private String profileImgUrl;
}
