package com.bdool.bdool.elastic.service;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.WildcardQuery;
import co.elastic.clients.json.JsonData;
import com.bdool.bdool.elastic.index.MessageIndex;
import com.bdool.bdool.elastic.index.ParticipantIndex;
import com.bdool.bdool.elastic.index.ProfileIndex;
import com.bdool.bdool.elastic.repository.ProfileSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ProfileSearchRepository profileSearchRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    public List<ProfileIndex> searchProfilesByName(String name) {
        return profileSearchRepository.findByName(name);
    }

    public List<ProfileIndex> searchProfiles(String keyword , int workspaceId ) {
        TermQuery termQuery = new TermQuery.Builder()
                .field("workspace_id")
                .value(workspaceId)
                .build();

        WildcardQuery nameQuery = new WildcardQuery.Builder()
                .field("nickname")
                .value("*" + keyword + "*")
                .caseInsensitive(true)
                .build();

        WildcardQuery positionQuery = new WildcardQuery.Builder()
                .field("position")
                .value("*" + keyword + "*")
                .caseInsensitive(true)
                .build();

        BoolQuery boolQuery = new BoolQuery.Builder()
                .filter(termQuery._toQuery())
                .should(nameQuery._toQuery())
                .should(positionQuery._toQuery())
                .minimumShouldMatch("1") // 최소 1개의 조건
                .build();
        //정렬
        NativeQuery query = NativeQuery.builder()
                .withQuery(boolQuery._toQuery())
                .withSort(s -> s
                        .field(f -> f
                                .field("name.keyword")
                                .order(SortOrder.Asc)
                        )
                )
                .build();
        SearchHits<ProfileIndex> searchHits = elasticsearchOperations.search(query, ProfileIndex.class);
        return searchHits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    public List<MessageIndex> searchMessages(String keyword, Long profileId, String startDate, String endDate) {
        List<String> channelIds = getChannelIdsByProfileId(profileId);
        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();

        for (String channelId : channelIds) {
            TermQuery termQuery = new TermQuery.Builder()
                    .field("channelId")
                    .value(channelId)
                    .build();
            boolQueryBuilder.should(termQuery._toQuery());
        }
        boolQueryBuilder.minimumShouldMatch("1");

        WildcardQuery wildcardQuery = new WildcardQuery.Builder()
                .field("content")
                .value("*" + keyword + "*")
                .build();
        boolQueryBuilder.must(wildcardQuery._toQuery());

        if (startDate != null || endDate != null) {
            RangeQuery.Builder rangeQuery = new RangeQuery.Builder().field("createdAt");

            if (startDate != null) {
                rangeQuery.gte(JsonData.of(startDate));
            }
            if (endDate != null) {
                rangeQuery.lte(JsonData.of(endDate));
            }

            boolQueryBuilder.filter(rangeQuery.build()._toQuery());
        }

        NativeQuery query = NativeQuery.builder()
                .withQuery(boolQueryBuilder.build()._toQuery())
                .withSort(s -> s
                        .field(f -> f
                                .field("createdAt")
                                .order(SortOrder.Desc)
                        )
                )
                .build();

        SearchHits<MessageIndex> searchHits = elasticsearchOperations.search(query, MessageIndex.class);
        return searchHits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
    public List<String> getChannelIdsByProfileId(Long profileId) {
        TermQuery termQuery = new TermQuery.Builder()
                .field("profile_id")
                .value(profileId)
                .build();

        NativeQuery query = NativeQuery.builder()
                .withQuery(termQuery._toQuery())
                .build();

        SearchHits<ParticipantIndex> searchHits =elasticsearchOperations.search(query, ParticipantIndex.class);
        return searchHits.getSearchHits().stream()
                .map(hit -> hit.getContent().getChannel_id())
                .collect(Collectors.toList());
    }

}
