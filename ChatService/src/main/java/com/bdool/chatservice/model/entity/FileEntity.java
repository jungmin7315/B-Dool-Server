package com.bdool.chatservice.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Document(collection = "file")
public class FileEntity {

    @Id
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
