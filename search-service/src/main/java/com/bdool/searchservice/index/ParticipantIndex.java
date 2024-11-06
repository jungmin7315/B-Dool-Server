package com.bdool.searchservice.index;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@Document(indexName = "participants")
public class ParticipantIndex {
    @Field(name = "participant_id", type = FieldType.Keyword)
    private String participantId;

    @Field(name = "channel_id", type = FieldType.Keyword)
    private String channelId;

    @Field(name = "profile_id", type = FieldType.Long)
    private Long profileId;





}
