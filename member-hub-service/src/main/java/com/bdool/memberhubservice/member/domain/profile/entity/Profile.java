package com.bdool.memberhubservice.member.domain.profile.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Table(name = "profiles")
@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // 이름
    private String nickname; // 별명
    private String position; // 직책
    private String status; // 상태메세지
    private String profilePictureUrl; // 프로필 이미지 URL
    private Boolean isOnline; // 온라인/오프라인 표시
    private Boolean isWorkspaceCreator;

    private Long memberId;

    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    private Long workspaceId;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt =  LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void updateProfile(String nickname, String position, String profilePictureUrl) {
        this.nickname = nickname;
        this.position = position;
        this.profilePictureUrl = profilePictureUrl;
        this.updatedAt = LocalDateTime.now();  // 업데이트 시간 갱신
    }

    public void updateStatus(String status) {
        this.status = status;
    }

    public void updateOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }
}

