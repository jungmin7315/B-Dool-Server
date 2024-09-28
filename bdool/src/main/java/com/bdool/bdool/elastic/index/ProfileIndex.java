package com.bdool.bdool.elastic.index;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@Document(indexName = "profiles")
public class ProfileIndex {
    @Id
    private Long id;
    private String name;
    private String nickname;
    private String position;
    private String status;
    private String profile_picture_url;
    private Boolean is_online;
    private String email;
    private Long workspace_id;
}


