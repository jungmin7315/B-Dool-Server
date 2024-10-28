package com.bdool.bdool.elastic.index;

import lombok.Getter;

import java.util.List;
@Getter
public class UnifiedSearchResponse {
    private List<ProfileIndex> profiles;
    private List<MessageIndex> messages;
    private List<FileIndex> files;

    public UnifiedSearchResponse(List<ProfileIndex> profiles, List<MessageIndex> messages, List<FileIndex> files) {
        this.profiles = profiles;
        this.messages = messages;
        this.files = files;
    }
}
