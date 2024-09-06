package com.bdool.chatservice.model.entity;

import com.bdool.chatservice.model.Enum.FileKind;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "file")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "file_id", nullable = false, updatable = false)
    private UUID fileId;

    @Column(name = "fname", length = 255, nullable = false)
    private String fname;

    @Column(name = "path", length = 255, nullable = false)
    private String path;

    @Column(name = "size", nullable = false)
    private Integer size;

    @Enumerated(EnumType.STRING)
    @Column(name = "kind", nullable = false)
    private FileKind kind;

    @Column(name = "uploaded_at", nullable = false)
    private LocalDateTime uploadedAt;

    @Column(name = "profile_id")
    private UUID profileId;

    @Column(name = "channel_id")
    private UUID channelId;

    @Column(name = "workspaces_id")
    private UUID workspacesId;
}
