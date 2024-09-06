package com.bdool.chatservice.model.entity;

import com.bdool.chatservice.model.Enum.FileKind;
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
    private UUID fileId;  // MongoDB에서는 @GeneratedValue가 필요하지 않습니다.

    private String fname;

    private String path;

    private Integer size;

    private FileKind kind;  // MongoDB에서는 @Enumerated 없이 Enum 값 저장이 가능합니다.

    private LocalDateTime uploadedAt;

    private UUID profileId;

    private UUID channelId;

    private UUID workspacesId;
}
