package com.bdool.bdool.elastic.index;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@Document(indexName = "participant")
public class ParticipantIndex {
    @Field(name = "participant_id", type = FieldType.Keyword)
    private String participantId;

    @Field(name = "channel_id", type = FieldType.Keyword)
    private String channelId;

    @Field(type = FieldType.Keyword)
    private String nickname;

    @Field(type = FieldType.Long)
    private Long favorited;

    @Field(type = FieldType.Long)
    private Long isOnline;

    @Field(name = "joined_at", type = FieldType.Keyword)
    private String joinedAt;

    @Field(name = "profile_id", type = FieldType.Long)
    private Long profileId;


}
