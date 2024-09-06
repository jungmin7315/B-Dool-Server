package com.bdool.memberhubservice.member.domain.profile.controller;

import com.bdool.memberhubservice.member.domain.profile.entity.Profile;
import com.bdool.memberhubservice.member.domain.profile.entity.model.ProfileModel;
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

    @PostMapping("/")
    public ResponseEntity<Profile> createProfile(@RequestBody ProfileModel profileModel) {
        return ResponseEntity.ok(profileService.save(profileModel));
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
}
