package com.bdool.bdool.elastic.controller;

import com.bdool.bdool.elastic.index.FileIndex;
import com.bdool.bdool.elastic.index.MessageIndex;
import com.bdool.bdool.elastic.index.ProfileIndex;
import com.bdool.bdool.elastic.index.UnifiedSearchResponse;
import com.bdool.bdool.elastic.service.SearchService;
import com.bdool.bdool.elastic.service.UnifiedSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/search")
public class SearchController {
    @Autowired
    private SearchService searchService;
    @Autowired
    private UnifiedSearchService unifiedSearchService;

    @GetMapping("/profile/{workspaceId}")
    public List<ProfileIndex> searchProfiles(@RequestParam String keyword, @PathVariable int workspaceId) {
        return searchService.searchProfiles(keyword, workspaceId);
    }

    @GetMapping("/message")
    public List<MessageIndex> searchMessages(@RequestParam String keyword,
                                             @RequestParam Long profileId,
                                             @RequestParam(required = false) String startDate,
                                             @RequestParam(required = false) String endDate ){
        return searchService.searchMessages(keyword, profileId,startDate, endDate);
    }
    @GetMapping("/file")
    public List<FileIndex> searchMessages(@RequestParam String keyword,
                                          @RequestParam Long profileId,
                                          @RequestParam(required = false) String extension){
        return searchService.searchFiles(keyword, profileId,extension);
    }

    @GetMapping("/unified/{workspaceId}")
    public UnifiedSearchResponse unifiedSearch(@PathVariable int workspaceId,
                                               @RequestParam String keyword,
                                               @RequestParam Long profileId,
                                               @RequestParam(required = false) String startDate,
                                               @RequestParam(required = false) String endDate,
                                               @RequestParam(required = false) String extension){

        return unifiedSearchService.unifiedSearch(workspaceId,keyword, profileId, startDate, endDate, extension);
    }

}


