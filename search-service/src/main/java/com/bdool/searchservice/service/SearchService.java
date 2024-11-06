package com.bdool.searchservice.service;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.json.JsonData;

import com.bdool.searchservice.index.FileIndex;
import com.bdool.searchservice.index.MessageIndex;
import com.bdool.searchservice.index.ParticipantIndex;
import com.bdool.searchservice.index.ProfileIndex;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final ElasticsearchOperations elasticsearchOperations;
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
                                .field("name")
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
                    .field("channel_id")
                    .value(channelId)
                    .build();
            boolQueryBuilder.should(termQuery._toQuery());
        }
        boolQueryBuilder.minimumShouldMatch("1");

        MatchQuery matchQuery = new MatchQuery.Builder()
                .field("content")
                .query(keyword)
                .operator(Operator.And)
                .build();
        boolQueryBuilder.must(matchQuery._toQuery());

        if (startDate != null || endDate != null) {
            RangeQuery.Builder rangeQuery = new RangeQuery.Builder().field("created_at");

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
                                .field("channel_id")
                                .order(SortOrder.Asc)
                        )
                )
                .withSort(s -> s
                        .field(f -> f
                                .field("send_date")
                                .order(SortOrder.Desc)
                        )
                )
                .build();

        SearchHits<MessageIndex> searchHits = elasticsearchOperations.search(query, MessageIndex.class);
        return searchHits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    public List<FileIndex> searchFiles(String keyword, Long profileId, String fileType){
        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();

        List<String> fileUrls = getFileUrlByProfileId(profileId);
        for(String fileUrl : fileUrls){
            TermQuery termQuery = new TermQuery.Builder()
                    .field("path")
                    .value(fileUrl)
                    .build();
            boolQueryBuilder.should(termQuery._toQuery());
        }
        boolQueryBuilder.minimumShouldMatch("1");

        MatchQuery matchQuery = new MatchQuery.Builder()
                .field("fname")
                .query(keyword)
                .operator(Operator.And)
                .build();
        boolQueryBuilder.must(matchQuery._toQuery());

        if(fileType != null){
            TermQuery termQuery2 = new TermQuery.Builder()
                    .field("file_type")
                    .value(fileType)
                    .build();
            boolQueryBuilder.filter(termQuery2._toQuery());
        }

        NativeQuery query = NativeQuery.builder()
                .withQuery(boolQueryBuilder.build()._toQuery())
                .withSort(s -> s
                        .field(f -> f
                                .field("uploaded_at")
                                .order(SortOrder.Desc)
                        )
                )

                .build();


        SearchHits<FileIndex> searchHits = elasticsearchOperations.search(query, FileIndex.class);
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
                .map(hit -> hit.getContent().getChannelId())
                .collect(Collectors.toList());
    }

    public List<String> getFileUrlByProfileId(Long profileId) {
        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();

        List<String> channelIds = getChannelIdsByProfileId(profileId);
        for (String channelId : channelIds) {
            TermQuery termQuery = new TermQuery.Builder()
                    .field("channel_id")
                    .value(channelId)
                    .build();
            boolQueryBuilder.should(termQuery._toQuery());
        }
        boolQueryBuilder.minimumShouldMatch("1");

        NativeQuery query = NativeQuery.builder()
                .withQuery(boolQueryBuilder.build()._toQuery())
                .build();

        SearchHits<MessageIndex> searchHits =elasticsearchOperations.search(query, MessageIndex.class);

        return searchHits.getSearchHits().stream()
                .map(hit -> hit.getContent().getFileURL())
                .collect(Collectors.toList());
    }
}
