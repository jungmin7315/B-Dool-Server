package com.bdool.memberhubservice.member.domain.profile.service;


import com.bdool.memberhubservice.member.domain.profile.entity.Profile;
import com.bdool.memberhubservice.member.domain.profile.entity.model.ProfileModel;
import com.bdool.memberhubservice.member.domain.profile.entity.model.ProfileResponse;
import com.bdool.memberhubservice.member.domain.profile.entity.model.ProfileUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface ProfileService {
    Profile save(ProfileModel profileModel, Long memberId);

    Profile saveByInvitation(ProfileModel profileModel, Long memberId, Long workspaceId);

    Optional<Profile> findById(Long profileId);

    List<ProfileResponse> findByWorkspaceId(Long workspaceId);

    List<Profile> findByMemberId(Long memberId);

    void deleteById(Long profileId);

    Profile update(Long profileId, ProfileUpdateRequest profileUpdateRequest);

    String updateStatus(Long profileId, String status);

    Boolean updateOnline(Long profileId, boolean isOnline);
}
