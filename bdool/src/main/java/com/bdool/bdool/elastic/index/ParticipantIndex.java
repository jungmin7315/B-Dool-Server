package com.bdool.bdool.elastic.index;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
@Getter
@Setter
@Document(indexName = "smaple_participant")
public class ParticipantIndex {
    @Id
    private String participant_id;
    private String channel_id;
    private Long profile_id;

}
