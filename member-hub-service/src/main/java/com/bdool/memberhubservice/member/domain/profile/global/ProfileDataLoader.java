package com.bdool.memberhubservice.member.domain.profile.global;

import com.bdool.memberhubservice.member.domain.profile.entity.Profile;
import com.bdool.memberhubservice.member.domain.profile.repository.ProfileRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ProfileDataLoader {
    @Bean
    public CommandLineRunner loadProfileData(ProfileRepository profileRepository) {
        return args -> {
            List<Profile> profiles = new ArrayList<>();

            for (int i = 0; i < 100; i++) {
                // 더미 데이터 생성
                Profile profile = Profile.builder()
                        .name("User" + i)
                        .nickname("Nickname" + i)
                        .position("Position" + i)
                        .status("Active")
                        .profilePictureUrl("https://example.com/image" + i + ".jpg")
                        .isOnline(false)  // 짝수는 온라인, 홀수는 오프라인
                        .isWorkspaceCreator(false)
                        .memberId((long) i + 1)
                        .email("user" + i + "@example.com")
                        .workspaceId(1L)  // 워크스페이스 ID 1로 설정
                        .build();

                profiles.add(profile);
            }

            // DB에 저장
            profileRepository.saveAll(profiles);
        };
    }
}
