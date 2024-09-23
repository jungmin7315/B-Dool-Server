package com.bdool.memberhubservice.member.domain.profile.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileNicknameResponse {
    private Long profileId;
    private Long workspaceId;
    private String nickname;
}
