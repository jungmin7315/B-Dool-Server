package com.bdool.memberhubservice.member.domain.profile.repository.querydsl;

import com.bdool.memberhubservice.member.domain.profile.entity.Profile;

import java.util.List;

public interface ProfileQueryDslRepositoryCustom {

    List<Profile> findProfilesByWorkspaceId(Long workspaceId);
}
