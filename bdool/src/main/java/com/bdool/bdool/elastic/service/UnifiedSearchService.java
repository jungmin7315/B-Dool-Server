package com.bdool.bdool.elastic.service;

import com.bdool.bdool.elastic.index.FileIndex;
import com.bdool.bdool.elastic.index.MessageIndex;
import com.bdool.bdool.elastic.index.ProfileIndex;
import com.bdool.bdool.elastic.index.UnifiedSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnifiedSearchService {

    private final SearchService searchService;

    @Autowired
    public UnifiedSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    public UnifiedSearchResponse unifiedSearch( int workspaceId ,String keyword, Long profileId,
                                               String startDate, String endDate, String extension){

        List<ProfileIndex> profiles = searchService.searchProfiles(keyword, workspaceId);
        List<MessageIndex> messages = searchService.searchMessages(keyword, profileId, startDate, endDate);
        List<FileIndex> files = searchService.searchFiles(keyword, profileId, extension);

        // 통합된 응답 생성
        return new UnifiedSearchResponse(profiles, messages, files);
    }
}

