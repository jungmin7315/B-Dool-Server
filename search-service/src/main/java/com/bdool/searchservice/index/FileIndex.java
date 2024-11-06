package com.bdool.searchservice.index;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Getter
@Setter
@Document(indexName = "files")
public class FileIndex {
    @Field(name ="file_id" )
    private String fileId;

    @Field
    private String fname;

    @Field(name = "original_file_name")
    private String originalFileName;

    @Field
    private String path;

    @Field
    private Long size;

    @Field
    private String extension;

    @Field(name = "uploaded_at")
    private String uploadedAt;

    @Field(name = "file_type")
    private String fileType;
}

