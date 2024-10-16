package com.bdool.bdool.elastic.index;

import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@Document(indexName = "messages")
public class MessageIndex {

    @Field(name = "message_id", type = FieldType.Keyword)
    private String messageId;

    @Field(name = "channel_id", type = FieldType.Keyword)
    private String channelId;

    @Field(type = FieldType.Text)
    private String content;

    @Field(name = "created_at", type = FieldType.Date)
    private String createdAt;

    @Field(name = "file_URL", type = FieldType.Keyword)
    private String fileURL;

    @Field(name = "is_deleted", type = FieldType.Long)
    private Long isDeleted;

    @Field(name = "is_edited", type = FieldType.Long)
    private Long isEdited;

    @Field(name = "profile_id", type = FieldType.Long)
    private Long profileId;

}