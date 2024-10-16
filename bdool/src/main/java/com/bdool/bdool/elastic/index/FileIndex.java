package com.bdool.bdool.elastic.index;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(indexName = "files")
public class FileIndex {
    @Field(type = FieldType.Keyword)
    private String file_id;

    @Field(type = FieldType.Text)
    private String fname;

    @Field(type = FieldType.Text)
    private String path;

    @Field(type = FieldType.Long)
    private Long size;

    @Field(type = FieldType.Keyword)
    private String extension;

    @Field(type = FieldType.Text)
    private String uploaded_at;

    @Field(type = FieldType.Long)
    private Long profile_id;

    @Field(type = FieldType.Keyword)
    private String channel_id;

    @Field(type = FieldType.Keyword)
    private String message_img_id;

    @Field(type = FieldType.Long)
    private Long workspaces_id;
}

