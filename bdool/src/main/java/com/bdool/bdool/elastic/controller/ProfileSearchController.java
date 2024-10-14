package com.bdool.bdool.elastic.controller;

import com.bdool.bdool.elastic.index.ProfileIndex;
import com.bdool.bdool.elastic.service.ProfileSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class ProfileSearchController {
    @Autowired
    private ProfileSearchService profileSearchService;

    @GetMapping("/name")
    public List<ProfileIndex> searchProfilesByName(@RequestParam String name) {
        return profileSearchService.searchProfilesByName(name);
    }

    @GetMapping("/{workspaceId}")
    public List<ProfileIndex> searchProfilesByKeyword(@RequestParam String keyword, @PathVariable int workspaceId) {
        return profileSearchService.searchProfilesByKeyword(keyword, workspaceId);
    }
}
