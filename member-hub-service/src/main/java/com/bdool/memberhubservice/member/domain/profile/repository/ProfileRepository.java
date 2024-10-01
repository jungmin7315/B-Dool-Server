package com.bdool.memberhubservice.member.domain.profile.repository;

import com.bdool.memberhubservice.member.domain.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    List<Profile> findProfilesByWorkspaceId(Long workspaceId);

    List<Profile> findByMemberId(Long memberId);
}
