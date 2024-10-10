package com.bdool.memberhubservice.member.domain.profile.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileMemberIdResponse {
    private String nickname;
    private String profilePictureUrl;
    private Long workspaceId;
}
