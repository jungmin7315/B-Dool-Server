package com.bdool.memberhubservice.member.domain.profile.service.impl;

import com.bdool.memberhubservice.member.domain.member.service.MemberService;
import com.bdool.memberhubservice.member.domain.profile.entity.Profile;
import com.bdool.memberhubservice.member.domain.profile.entity.model.ProfileModel;
import com.bdool.memberhubservice.member.domain.profile.repository.ProfileRepository;
import com.bdool.memberhubservice.member.domain.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final MemberService memberService;

    @Override
    public Profile save(ProfileModel profileModel, Long memberId, boolean isWorkspaceCreator) {
        Profile profile = Profile.builder()
                .name(profileModel.getName())
                .nickname(profileModel.getNickname())
                .profilePictureUrl(profileModel.getProfilePictureUrl())
                .memberId(memberId)
                .isWorkspaceCreator(isWorkspaceCreator)
                .build();
        return profileRepository.save(profile);
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
}
