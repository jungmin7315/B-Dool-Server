package com.bdool.memberhubservice.member.domain.profile.controller;

import com.bdool.memberhubservice.member.domain.profile.entity.Profile;
import com.bdool.memberhubservice.member.domain.profile.entity.model.ProfileModel;
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
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/{memberId}")
    public ResponseEntity<Profile> createProfile(@PathVariable Long memberId,
                                                 @RequestBody ProfileModel profileModel,
                                                 boolean isWorkspaceCreator) {
        return ResponseEntity.ok(profileService.save(profileModel, memberId, isWorkspaceCreator));
    }

    @PostMapping("/{memberId}&&{workspaceId}/invited")
    public ResponseEntity<Profile> createProfileByInvitation(@PathVariable Long memberId,
                                                             @PathVariable Long workspaceId,
                                                             @RequestBody ProfileModel profileModel,
                                                             boolean isWorkspaceCreator) {
        return ResponseEntity.ok(profileService.saveByInvitation(profileModel, memberId, workspaceId, isWorkspaceCreator));
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<Optional<Profile>> getProfileById(@PathVariable Long profileId) {
        return ResponseEntity.ok(profileService.findById(profileId));
    }

    @GetMapping("/")
    public ResponseEntity<List<Profile>> getAllProfile() {
        return ResponseEntity.ok(profileService.findAll());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getProfileCount() {
        return ResponseEntity.ok(profileService.count());
    }

    @GetMapping("/exists/{profileId}")
    public ResponseEntity<Boolean> checkProfileExists(@PathVariable Long profileId) {
        return ResponseEntity.ok(profileService.existsById(profileId));
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
