package com.bdool.searchservice.controller;


import com.bdool.searchservice.index.FileIndex;
import com.bdool.searchservice.index.MessageIndex;
import com.bdool.searchservice.index.ProfileIndex;
import com.bdool.searchservice.index.UnifiedSearchResponse;
import com.bdool.searchservice.service.SearchService;
import com.bdool.searchservice.service.UnifiedSearchService;
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
                                          @RequestParam(required = false) String fileType){
        return searchService.searchFiles(keyword, profileId,fileType);
    }

    @GetMapping("/unified/{workspaceId}")
    public UnifiedSearchResponse unifiedSearch(@PathVariable int workspaceId,
                                               @RequestParam String keyword,
                                               @RequestParam Long profileId,
                                               @RequestParam(required = false) String startDate,
                                               @RequestParam(required = false) String endDate,
                                               @RequestParam(required = false) String fileType){

        return unifiedSearchService.unifiedSearch(workspaceId,keyword, profileId, startDate, endDate, fileType);
    }


    @GetMapping("/messages/{profileId}")
    public ResponseEntity<List<String>> getMessagesByProfileId(@PathVariable Long profileId) {
        List<String> messageIds = searchService.getFileUrlByProfileId(profileId);
        return ResponseEntity.ok(messageIds);
    }

    @GetMapping("/channel/{profileId}")
    public ResponseEntity<List<String>> getChannelIdsByProfileId (@PathVariable Long profileId) {
        List<String> ids = searchService.getChannelIdsByProfileId(profileId);
        return ResponseEntity.ok(ids);
    }


}


