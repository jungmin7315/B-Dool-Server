package com.bdool.chatservice.model.entity;

import com.bdool.chatservice.model.Enum.EntityType;
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
    private UUID fileId; // UUID 자동 생성

    private String fname; // 파일을 넣어주면 자동으로 추출해서 넣어줌

    private String path; // 파일을 넣어주면 자동으로 추출해서 넣어줌

    private Integer size; // 파일을 넣어주면 자동으로 추출해서 넣어줌

    private String extension; // 파일을 넣어주면 자동으로 추출해서 넣어줌

    private LocalDateTime uploadedAt; // LocalDateTime.now()로 처리

    private Long profileImgId; // 받아야 하는 필드

    private UUID channelImgId; // 받아야 하는 필드

    private Long workspacesImgId; // 받아야 하는 필드

    private UUID messageImgId; // 받아야 하는 필드

    private EntityType entityType; // 받아야 하는 필드
}
