package com.bdool.memberhubservice.member.domain.profile.service.impl;

import com.bdool.memberhubservice.member.domain.member.entity.Member;
import com.bdool.memberhubservice.member.domain.member.service.MemberService;
import com.bdool.memberhubservice.member.domain.profile.entity.Profile;
import com.bdool.memberhubservice.member.domain.profile.entity.model.*;
import com.bdool.memberhubservice.member.domain.profile.repository.ProfileRepository;
import com.bdool.memberhubservice.member.domain.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final MemberService memberService;
    private final ProfileSSEService sseService;

    @Override

    public Profile save(ProfileModel profileModel, Long memberId, boolean isWorkspaceCreator) {
        Member member = memberService.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("member not found"));
        Profile profile = Profile.builder()
                .name(profileModel.getName())
                .nickname(profileModel.getNickname())
                .profilePictureUrl(profileModel.getProfilePictureUrl())
                .memberId(member.getId())
                .isWorkspaceCreator(isWorkspaceCreator)
                .email(member.getEmail())
                .isOnline(false)
                .build();
        return profileRepository.save(profile);
    }

    @Override
    public Optional<Profile> findById(Long profileId) {
        return profileRepository.findById(profileId);
    }

    @Override
    public long count() {
        return profileRepository.count();
    }

    @Override
    public List<ProfileResponse> findByWorkspaceId(Long workspaceId) {
//        return profileRepository.findProfilesByWorkspaceId(workspaceId);
        List<Profile> profiles = profileRepository.findProfilesByWorkspaceId(workspaceId);
        return profiles.stream()
                .map(this::convertToProfileResponse)  // 변환 메서드 사용
                .collect(Collectors.toList());
    }

    private ProfileResponse convertToProfileResponse(Profile profile) {
        return new ProfileResponse(
                profile.getId(),
                profile.getNickname(),
                profile.getIsOnline()
        );
    }


    @Override
    public boolean existsById(Long profileId) {
        return profileRepository.existsById(profileId);
    }

    @Override
    public void deleteById(Long profileId) {
        profileRepository.deleteById(profileId);
    }

    @Override
    public Profile update(Long profileId, ProfileUpdateRequest profileUpdateRequest) {
        Profile findProfile = profileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("profile not found"));
        findProfile.updateProfile(profileUpdateRequest.getNickname(),
                profileUpdateRequest.getPosition(),
                profileUpdateRequest.getProfilePictureUrl());
        profileRepository.save(findProfile);

        ProfileNicknameResponse profileNicknameResponse = new ProfileNicknameResponse(
                profileId,
                findProfile.getWorkspaceId(),
                findProfile.getNickname());
        sseService.notifyNicknameChange(profileNicknameResponse);
        return findProfile;
    }

    @Override
    public String updateStatus(Long profileId, String status) {
        Profile findProfile = profileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("profile not found"));
        findProfile.updateStatus(status);
        profileRepository.save(findProfile);

        return findProfile.getStatus();
    }

    @Override
    public Boolean updateOnline(Long profileId, boolean isOnline) {
        Profile findProfile = profileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("profile not found"));
        findProfile.updateOnline(isOnline);
        profileRepository.save(findProfile);

        ProfileOnlineResponse profileOnlineResponse = new ProfileOnlineResponse(
                profileId, findProfile.getWorkspaceId(), findProfile.getIsOnline()
        );
        sseService.notifyOnlineChange(profileOnlineResponse);

        return findProfile.getIsOnline();
    }
}
