package com.bdool.memberhubservice.member.domain.profile.controller;

import com.bdool.memberhubservice.member.domain.profile.entity.model.*;
import com.bdool.memberhubservice.member.domain.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
@CrossOrigin
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/{memberId}")
    public ResponseEntity<ProfileResponse> createProfile(@PathVariable Long memberId,
                                                         @RequestBody ProfileModel profileModel) {
        return ResponseEntity.ok(profileService.save(profileModel, memberId));
    }

    @PostMapping("/{memberId}&&{workspaceId}/invited")
    public ResponseEntity<ProfileResponse> createProfileByInvitation(@PathVariable Long memberId,
                                                                     @PathVariable Long workspaceId,
                                                                     @RequestBody ProfileModel profileModel) {
        return ResponseEntity.ok(profileService.saveByInvitation(profileModel, memberId, workspaceId));
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<ProfileFindResponse> getProfileById(@PathVariable Long profileId) {
        return ResponseEntity.ok(profileService.getProfileById(profileId));
    }

    @GetMapping("/workspace/{workspaceId}")
    public ResponseEntity<List<ProfileResponse>> getProfileByWorkspaceId(@PathVariable Long workspaceId) {
        return ResponseEntity.ok(profileService.getProfileByWorkspaceId(workspaceId));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<ProfileResponseMemberId>> getProfileByMemberId(@PathVariable Long memberId) {
        return ResponseEntity.ok(profileService.getProfileByMemberId(memberId));
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<Void> deleteProfileById(@PathVariable Long profileId) {
        profileService.deleteById(profileId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{profileId}")
    public ResponseEntity<ProfileFindResponse> updateProfile(@PathVariable Long profileId, @RequestBody ProfileUpdateRequest profileUpdateRequest) {
        return ResponseEntity.ok(profileService.update(profileId, profileUpdateRequest));
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
