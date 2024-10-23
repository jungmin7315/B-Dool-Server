package com.bdool.chatservice.model.domain;

import com.bdool.chatservice.model.Enum.EntityType;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Data
public class FileModel {
    private UUID fileId;

    private String fname;

    private String originalFileName;

    private String path;

    private Integer size;

    private String extension;

    private LocalDateTime uploadedAt;

    private Long profileImgId;

    private UUID channelImgId;

    private Long workspacesImgId;

    private UUID messageImgId;

    private EntityType entityType;
}
