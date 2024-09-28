package com.bdool.bdool.elastic.controller;

import com.bdool.bdool.elastic.index.ProfileIndex;
import com.bdool.bdool.elastic.service.ProfileSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class ProfileSearchController {
    @Autowired
    private ProfileSearchService profileSearchService;

    @GetMapping("name")
    public List<ProfileIndex> searchProfilesByName(@RequestParam String name) {
        return profileSearchService.searchProfilesByName(name);
    }

    @GetMapping()
    public List<ProfileIndex> searchProfilesByKeyword(@RequestParam String keyword) {
        return profileSearchService.searchProfilesByKeyword(keyword);
    }
}
