package com.bdool.chatservice.model.entity;

import com.bdool.chatservice.model.Enum.EntityType;
import com.bdool.chatservice.model.Enum.FileStatus;
import com.bdool.chatservice.model.Enum.FileType;
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
    private UUID fileId; // 파일 고유 ID (UUID)

    private String fname; // 저장될 파일명 (UUID가 추가된 파일명)

    private String originalFileName; // 업로드된 파일의 원본 파일명

    private String path; // 파일 경로 (S3 URL 등)

    private Integer size; // 파일 크기 (바이트 단위)

    private String extension; // 파일 확장자 (예: jpg, pdf)

    private LocalDateTime uploadedAt; // 파일 업로드 시간

    private EntityType entityType; // 파일이 속한 엔터티의 타입 (예: PROFILE, WORKSPACE, MESSAGE)

    private FileType fileType; // 파일 유형 (예: IMAGE, DOCUMENT, VIDEO 등)

    private String md5Hash; // 파일 무결성 확인을 위한 MD5 해시 (Optional)

    private FileStatus status; // 파일 상태 (예: "ACTIVE", "DELETED", "ARCHIVED")
}
