package com.bdool.bdool.elastic.controller;

import com.bdool.bdool.elastic.index.MessageIndex;
import com.bdool.bdool.elastic.index.ProfileIndex;
import com.bdool.bdool.elastic.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping("/name")
    public List<ProfileIndex> searchProfilesByName(@RequestParam String name) {
        return searchService.searchProfilesByName(name);
    }

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

}
