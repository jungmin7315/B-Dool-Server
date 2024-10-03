package com.bdool.memberhubservice.member.domain.profile.entity.model;

import com.bdool.memberhubservice.member.domain.profile.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileFindResponse {

    private Long id;
    private String name;
    private String nickname; // 별명
    private String position; // 직책
    private String status; // 상태메세지
    private String profilePictureUrl; // 프로필 이미지 URL
    private Boolean isOnline; // 온라인/오프라인 표시
    private Boolean isWorkspaceCreator;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long workspaceId;

    public static ProfileFindResponse fromEntity(Profile profile) {
        return new ProfileFindResponse(
                profile.getId(),
                profile.getName(),
                profile.getNickname(),
                profile.getPosition(),
                profile.getStatus(),
                profile.getProfilePictureUrl(),
                profile.getIsOnline(),
                profile.getIsWorkspaceCreator(),
                profile.getEmail(),
                profile.getCreatedAt(),
                profile.getUpdatedAt(),
                profile.getWorkspaceId()
        );
    }
}
