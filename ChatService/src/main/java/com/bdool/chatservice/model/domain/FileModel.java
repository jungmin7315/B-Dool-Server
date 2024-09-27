package com.bdool.chatservice.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Data
public class FileModel {
    private UUID fileId;

    private String fname;

    private String path;

    private Integer size;

    private String extension;

    private LocalDateTime uploadedAt;

    private UUID profileId;

    private UUID channelId;

    private UUID workspacesId;
}
