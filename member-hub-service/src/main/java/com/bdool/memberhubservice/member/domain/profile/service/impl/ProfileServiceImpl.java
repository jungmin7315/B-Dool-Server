package com.bdool.memberhubservice.member.domain.profile.service.impl;

import com.bdool.memberhubservice.member.domain.member.entity.Member;
import com.bdool.memberhubservice.member.domain.member.service.MemberService;
import com.bdool.memberhubservice.member.domain.profile.entity.Profile;
import com.bdool.memberhubservice.member.domain.profile.entity.model.*;
import com.bdool.memberhubservice.member.domain.profile.repository.ProfileRepository;
import com.bdool.memberhubservice.member.domain.profile.service.ProfileService;
import com.bdool.memberhubservice.notification.NotificationModel;
import com.bdool.memberhubservice.notification.NotificationTargetType;
import com.bdool.memberhubservice.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final MemberService memberService;
    private final ProfileSSEService sseService;
    private final WebClient.Builder webClientBuilder;

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
        return profileRepository.save(profile);
    }

    @Override
    public Profile saveByInvitation(ProfileModel profileModel, Long memberId, Long workspaceId, boolean isWorkspaceCreator) {
        Member member = memberService.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("member not found"));
        Profile profile = Profile.builder()
                .name(profileModel.getName())
                .nickname(profileModel.getNickname())
                .profilePictureUrl(profileModel.getProfilePictureUrl())
                .memberId(member.getId())
                .workspaceId(workspaceId)
                .isWorkspaceCreator(isWorkspaceCreator)
                .email(member.getEmail())
                .build();
        Profile savedProfile = profileRepository.save(profile);

        List<Profile> profilesInWorkspace = profileRepository.findProfilesByWorkspaceId(workspaceId);

        for (Profile profileInWorkspace : profilesInWorkspace) {
            NotificationModel notificationModel = createNotificationModel(profileModel, workspaceId, profileInWorkspace.getId());
            sendNotification(notificationModel);
        }

        return savedProfile;
    }

    private NotificationModel createNotificationModel(ProfileModel profileModel, Long workspaceId, Long recipientProfileId) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("nickname", profileModel.getNickname());
        metadata.put("workspaceId", workspaceId);

        return NotificationModel.builder()
                .profileId(recipientProfileId)  // 알림을 받을 사용자 프로필 ID
                .notificationType(NotificationType.WORKSPACE_ENTRY)  // 알림 유형
                .message(profileModel.getNickname() + "님이 워크스페이스에 입장했습니다.")  // 알림 메시지
                .metadata(metadata)
                .targetType(NotificationTargetType.WORKSPACE)  // 타겟 유형
                .targetId(workspaceId)  // 워크스페이스 ID
                .build();
    }

    private void sendNotification(NotificationModel notificationModel) {
        WebClient webClient = webClientBuilder.build();

        webClient.post()
                .uri("http://localhost:8080/notifications")  // 알림 서비스의 엔드포인트
                .bodyValue(notificationModel)  // 요청 바디에 알림 데이터 설정
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(response -> System.out.println("알림 전송 성공"))
                .doOnError(error -> System.err.println("알림 전송 실패: " + error.getMessage()))
                .subscribe();  // 비동기 호출 실행
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
