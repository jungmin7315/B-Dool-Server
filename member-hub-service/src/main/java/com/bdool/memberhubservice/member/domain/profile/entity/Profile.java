package com.bdool.memberhubservice.member.domain.profile.entity;

import com.bdool.memberhubservice.member.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name = "profiles")
@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name; // 이름
    private String nickname; // 별명
    private String position; // 직책
    private String status; // 상태메세지
    private String profilePictureUrl; // 프로필 이미지 URL
    private Boolean isOnline; // 온라인/오프라인 표시
    private Boolean isWorkspaceCreator;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    private Long workspaceId;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}

