package com.bdool.bdool.elastic.index;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Table(name = "profiles")
@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // 이름
    private String nickname; // 별명
    private String position; // 직책
    private String status; // 상태메세지
    private String profilePictureUrl; // 프로필 이미지 URL
    private Boolean isOnline; // 온라인/오프라인 표시
    private Boolean isWorkspaceCreator; // 워크스페이스 생성자 여부

    private Long memberId;

    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long workspaceId;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
