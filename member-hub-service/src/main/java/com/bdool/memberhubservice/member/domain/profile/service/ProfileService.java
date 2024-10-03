package com.bdool.memberhubservice.member.domain.profile.service;


import com.bdool.memberhubservice.member.domain.profile.entity.Profile;
import com.bdool.memberhubservice.member.domain.profile.entity.model.*;

import java.util.List;

public interface ProfileService {
    ProfileResponse save(ProfileModel profileModel, Long memberId);

    ProfileResponse saveByInvitation(ProfileModel profileModel, Long memberId, Long workspaceId);

    ProfileFindResponse findById(Long profileId);

    List<ProfileResponse> findByWorkspaceId(Long workspaceId);

    List<ProfileResponseMemberId> findByMemberId(Long memberId);

    void deleteById(Long profileId);

    ProfileFindResponse update(Long profileId, ProfileUpdateRequest profileUpdateRequest);

    String updateStatus(Long profileId, String status);

    Boolean updateOnline(Long profileId, boolean isOnline);
}
