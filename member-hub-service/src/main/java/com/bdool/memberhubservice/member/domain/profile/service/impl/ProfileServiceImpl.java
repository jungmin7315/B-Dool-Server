package com.bdool.memberhubservice.member.domain.profile.service.impl;

import com.bdool.memberhubservice.member.domain.member.entity.Member;
import com.bdool.memberhubservice.member.domain.member.service.MemberService;
import com.bdool.memberhubservice.member.domain.profile.entity.Profile;
import com.bdool.memberhubservice.member.domain.profile.entity.model.*;
import com.bdool.memberhubservice.member.domain.profile.repository.ProfileRepository;
import com.bdool.memberhubservice.member.domain.profile.service.ProfileService;
import com.bdool.memberhubservice.notification.NotificationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bdool.memberhubservice.member.domain.profile.service.NotificationServiceHelper.createNotificationModel;
import static com.bdool.memberhubservice.member.domain.profile.service.NotificationServiceHelper.sendNotification;
import static com.bdool.memberhubservice.member.domain.profile.service.ParticipantServiceHelper.*;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final MemberService memberService;
    private final ProfileSSEService sseService;
    private final WebClient webClient;
    @Value("${notification-service.url}")
    private String notificationServiceUrl;
    @Value("${channel-service.url}")
    private String channelServiceUrl;

    @Override
    public Profile save(ProfileModel profileModel, Long memberId) {
        Member member = memberService.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("member not found"));
        Profile profile = Profile.builder()
                .name(profileModel.getName())
                .nickname(profileModel.getNickname())
                .profilePictureUrl(profileModel.getProfilePictureUrl())
                .memberId(member.getId())
                .isWorkspaceCreator(true)
                .email(member.getEmail())
                .build();
        return profileRepository.save(profile);
    }

    @Override
    public Profile saveByInvitation(ProfileModel profileModel, Long memberId, Long workspaceId) {
        Member member = memberService.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("member not found"));
        Profile profile = Profile.builder()
                .name(profileModel.getName())
                .nickname(profileModel.getNickname())
                .profilePictureUrl(profileModel.getProfilePictureUrl())
                .memberId(member.getId())
                .isOnline(profileModel.getIsOnline())
                .workspaceId(workspaceId)
                .isWorkspaceCreator(false)
                .email(member.getEmail())
                .build();
        Profile savedProfile = profileRepository.save(profile);

        List<Profile> profilesInWorkspace = profileRepository.findProfilesByWorkspaceId(workspaceId);
        for (Profile profileInWorkspace : profilesInWorkspace) {
            NotificationModel notificationModel = createNotificationModel(profileModel, workspaceId, profileInWorkspace.getId());

            sendNotification(webClient, notificationServiceUrl, notificationModel);
        }

        return savedProfile;
    }


    @Override
    public Optional<Profile> findById(Long profileId) {
        return profileRepository.findById(profileId);
    }


    @Override
    public List<ProfileResponse> findByWorkspaceId(Long workspaceId) {
        List<Profile> profiles = profileRepository.findProfilesByWorkspaceId(workspaceId);
        return profiles.stream()
                .map(this::convertToProfileResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<Profile> findByMemberId(Long memberId) {
        return profileRepository.findByMemberId(memberId);
    }

    private ProfileResponse convertToProfileResponse(Profile profile) {
        return new ProfileResponse(
                profile.getId(),
                profile.getNickname(),
                profile.getIsOnline()
        );
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
        sendNicknameToChannelService(webClient, channelServiceUrl, profileId, findProfile.getNickname());
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
        sendOnlineStatusToChannelService(webClient, channelServiceUrl, profileId, isOnline);

        return findProfile.getIsOnline();
    }
}