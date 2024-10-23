package com.bdool.memberhubservice.member.domain.profile.service;


import com.bdool.memberhubservice.member.domain.profile.entity.Profile;
import com.bdool.memberhubservice.member.domain.profile.entity.model.*;

import java.util.List;
import java.util.Optional;

public interface ProfileService {
    ProfileResponse save(ProfileModel profileModel, Long memberId);

    ProfileResponse saveByInvitation(ProfileModel profileModel, Long memberId, Long workspaceId);

    ProfileFindResponse getProfileById(Long profileId);

    List<ProfileResponse> getProfileByWorkspaceId(Long workspaceId);

    List<ProfileMemberIdResponse> getProfileByMemberId(Long memberId);

    void deleteById(Long profileId);

    ProfileFindResponse update(Long profileId, ProfileUpdateRequest profileUpdateRequest);

    String updateStatus(Long profileId, String status);

    Boolean updateOnline(Long profileId, Boolean isOnline);

    Optional<Profile> findProfileById(Long invitorId);

    List<Profile> getProfileByToken(String accessToken);

    Profile getProfileByMemberIdAndWorkspaceId(Long memberId, Long workspaceId);
}
