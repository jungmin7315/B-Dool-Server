package com.bdool.workspaceservice.workspace.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "workspaces")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String name;  // 워크스페이스 이름

    @Column
    private String description;  // 워크스페이스 설명

    @Column(nullable = false, unique = true)
    private String url;  // 워크스페이스 URL

    @Column(name = "workspace_image_url")
    private String workspaceImageUrl;  // 워크스페이스 이미지 URL

    @Column(updatable = false)
    private LocalDateTime createdAt;  // 워크스페이스 생성 시각

    @Column(name = "owner_id", nullable = false)
    private Long ownerId; // 소유자 멤버 ID

    // 생성 시각을 자동으로 설정하는 메서드
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
