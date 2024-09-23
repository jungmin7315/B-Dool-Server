package com.bdool.memberhubservice.member.domain.profile.repository.querydsl.impl;

import com.bdool.memberhubservice.member.domain.profile.entity.Profile;
import com.bdool.memberhubservice.member.domain.profile.repository.querydsl.ProfileQueryDslRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.bdool.memberhubservice.member.domain.profile.entity.QProfile.profile;

@Repository
@RequiredArgsConstructor
public class ProfileQueryDslRepositoryImpl implements ProfileQueryDslRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Profile> findProfilesByWorkspaceId(Long workspaceId) {
        return queryFactory
                .select(profile)
                .from(profile)
                .where(profile.workspaceId.eq(workspaceId))
                .fetch();
    }
}