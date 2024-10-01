package com.bdool.bdool.elastic.service;

import co.elastic.clients.elasticsearch._types.SortOrder;
import com.bdool.bdool.elastic.index.ProfileIndex;
import com.bdool.bdool.elastic.repository.ProfileSearchRepository;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileSearchService {

    @Autowired
    private ProfileSearchRepository profileSearchRepository;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    public List<ProfileIndex> searchProfilesByName(String name) {
        return profileSearchRepository.findByName(name);
    }

    public List<ProfileIndex> searchProfilesByKeyword(String keyword ,int workspaceId ) {
        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> b
                                .filter(f -> f
                                        .term(t -> t
                                                .field("workspace_id")
                                                .value(workspaceId))
                                )
                                .should(s -> s
                                        .wildcard(w -> w
                                                .field("name")
                                                .value("*" + keyword + "*")
                                                .caseInsensitive(true)
                                        )
                                )
                                .should(s -> s
                                        .wildcard(w -> w
                                                .field("position")
                                                .value("*" + keyword + "*")
                                                .caseInsensitive(true)
                                        )
                                )
                                .minimumShouldMatch("1")  // 최소 1개
                        )
                )
                .withSort(s -> s
                        .field(f -> f
                                .field("name.keyword")
                                .order(SortOrder.Asc)
                        )
                )
                .build();
        SearchHits<ProfileIndex> searchHits = elasticsearchOperations.search(searchQuery, ProfileIndex.class);

        return searchHits.stream()
                .map(hit -> hit.getContent())
                .collect(Collectors.toList());
    }
}
