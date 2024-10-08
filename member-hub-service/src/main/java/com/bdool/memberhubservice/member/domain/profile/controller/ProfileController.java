package com.bdool.memberhubservice.member.domain.profile.controller;

import com.bdool.memberhubservice.member.domain.profile.entity.Profile;
import com.bdool.memberhubservice.member.domain.profile.entity.model.ProfileModel;
import com.bdool.memberhubservice.member.domain.profile.entity.model.ProfileResponse;
import com.bdool.memberhubservice.member.domain.profile.entity.model.ProfileUpdateRequest;
import com.bdool.memberhubservice.member.domain.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
@CrossOrigin
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/{memberId}")
    public ResponseEntity<Profile> createProfile(@PathVariable Long memberId,
                                                 @RequestBody ProfileModel profileModel) {
        return ResponseEntity.ok(profileService.save(profileModel, memberId));
    }

    @PostMapping("/{memberId}&&{workspaceId}/invited")
    public ResponseEntity<Profile> createProfileByInvitation(@PathVariable Long memberId,
                                                             @PathVariable Long workspaceId,
                                                             @RequestBody ProfileModel profileModel) {
        return ResponseEntity.ok(profileService.saveByInvitation(profileModel, memberId, workspaceId));
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<Optional<Profile>> getProfileById(@PathVariable Long profileId) {
        return ResponseEntity.ok(profileService.findById(profileId));
    }

    @GetMapping("/workspace/{workspaceId}")
    public ResponseEntity<List<ProfileResponse>> getProfileByWorkspaceId(@PathVariable Long workspaceId) {
        return ResponseEntity.ok(profileService.findByWorkspaceId(workspaceId));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Profile>> getProfileByMemberId(@PathVariable Long memberId) {
        return ResponseEntity.ok(profileService.findByMemberId(memberId));
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<Void> deleteProfileById(@PathVariable Long profileId) {
        profileService.deleteById(profileId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{profileId}")
    public ResponseEntity<Profile> updateProfile(@PathVariable Long profileId, @RequestBody ProfileUpdateRequest profileUpdateRequest) {
        Profile updatedProfile = profileService.update(profileId, profileUpdateRequest);
        return ResponseEntity.ok(updatedProfile);
    }

    @PatchMapping("/{profileId}/status")
    public ResponseEntity<String> updateStatus(@PathVariable Long profileId, @RequestParam String status) {
        return ResponseEntity.ok(profileService.updateStatus(profileId, status));
    }

    @PatchMapping("/{profileId}/online")
    public ResponseEntity<Boolean> updateOnlineStatus(@PathVariable Long profileId, @RequestParam boolean isOnline) {
        return ResponseEntity.ok(profileService.updateOnline(profileId, isOnline));
    }
}
