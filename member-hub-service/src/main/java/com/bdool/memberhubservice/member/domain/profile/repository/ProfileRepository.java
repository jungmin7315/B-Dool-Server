package com.bdool.memberhubservice.member.domain.profile.repository;

import com.bdool.memberhubservice.member.domain.profile.entity.Profile;
import com.bdool.memberhubservice.member.domain.profile.repository.querydsl.ProfileQueryDslRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long>, ProfileQueryDslRepositoryCustom {

    void findByWorkspaceId(Long workspaceId);
}
