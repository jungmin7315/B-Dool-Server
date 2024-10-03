package com.bdool.bdool.elastic.index;

import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
@Getter
@Setter
@Document(indexName = "sample_message")
public class MessageIndex {
    @Id
    private String messageId;
    private String channelId;
    private String content;
    //@Field(type = FieldType.Date, format = DateFormat.date_time)
    private String createdAt;
    private Boolean isEdited;
    private Boolean isDeleted;      // 삭제 여부 (TINYINT(1))
    private String parentMessageId;
    private String fileId;
    private Long profileId;



}
