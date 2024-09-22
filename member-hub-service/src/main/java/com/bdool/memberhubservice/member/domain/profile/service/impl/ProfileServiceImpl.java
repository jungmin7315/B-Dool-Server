package com.bdool.memberhubservice.member.domain.profile.service.impl;

import com.bdool.memberhubservice.member.domain.member.entity.Member;
import com.bdool.memberhubservice.member.domain.member.service.MemberService;
import com.bdool.memberhubservice.member.domain.profile.entity.Profile;
import com.bdool.memberhubservice.member.domain.profile.entity.model.ProfileModel;
import com.bdool.memberhubservice.member.domain.profile.entity.model.ProfileUpdateRequest;
import com.bdool.memberhubservice.member.domain.profile.repository.ProfileRepository;
import com.bdool.memberhubservice.member.domain.profile.service.ProfileService;
import com.bdool.memberhubservice.notification.domain.notification.entity.NotificationType;
import com.bdool.memberhubservice.notification.domain.setting.service.NotificationTargetSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final MemberService memberService;
    private final NotificationTargetSettingService settingService;

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
                .build();
        profileRepository.save(profile);
        initializeDefaultSettings(profile.getId());
        return profile;
    }

    @Override
    public Optional<Profile> findById(Long profileId) {
        return profileRepository.findById(profileId);
    }

    @Override
    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    @Override
    public long count() {
        return profileRepository.count();
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
        return findProfile.getIsOnline();
    }

    private void initializeDefaultSettings(Long profileId) {
        List<NotificationType> notificationTypes = Arrays.asList(
                NotificationType.CHATTING,
                NotificationType.EVENT,
                NotificationType.SYSTEM,
                NotificationType.OTHER
        );

        for (NotificationType type : notificationTypes) {
            settingService.save(profileId, type, true);
        }
    }
}
