package com.bdool.bdool.elastic.repository;

import com.bdool.bdool.elastic.index.ProfileIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProfileSearchRepository extends ElasticsearchRepository<ProfileIndex, String> {
    List<ProfileIndex> findByName(String name);
}
