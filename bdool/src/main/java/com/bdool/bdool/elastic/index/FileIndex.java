package com.bdool.bdool.elastic.index;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import java.time.LocalDateTime;

@Getter
@Setter
@Document(indexName = "files")
public class FileIndex {
    private String file_id;  // UUID
    private String fname;  // 파일 이름
    private String path;  // 파일 경로
    private Integer size;
    private String extension;  // 파일 확장자
    private LocalDateTime uploadedAt;
    private Long profile_id;
    private String channel_id;
    private String message_img_id;
    private Long workspaces_id;

}
