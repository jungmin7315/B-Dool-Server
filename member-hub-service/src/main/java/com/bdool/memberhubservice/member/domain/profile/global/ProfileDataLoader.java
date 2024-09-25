//package com.bdool.memberhubservice.member.domain.profile.global;
//
//import com.bdool.memberhubservice.member.domain.profile.entity.Profile;
//import com.bdool.memberhubservice.member.domain.profile.repository.ProfileRepository;
//import jakarta.annotation.PostConstruct;
//import jakarta.annotation.PreDestroy;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class ProfileDataLoader {
//
//    private final ProfileRepository profileRepository;
//
//    // 애플리케이션 시작 시 더미 데이터를 로드
//    @PostConstruct
//    public void loadDummyProfilesOnStartup() {
//        System.out.println("Application started, loading dummy profiles...");
//        createDummyProfiles();  // 더미 데이터 생성 메서드 호출
//    }
//
//    // 애플리케이션 종료 시 더미 데이터를 삭제
//    @PreDestroy
//    public void removeDummyProfilesOnShutdown() {
//        System.out.println("Application shutting down, removing dummy profiles...");
//        deleteDummyProfiles();  // 더미 데이터 삭제 메서드 호출
//    }
//
//    // 더미 프로필 생성
//    private void createDummyProfiles() {
//        for (long workspaceId = 1; workspaceId <= 10; workspaceId++) {  // 워크스페이스 ID 1부터 10까지
//            for (int i = 1; i <= 10; i++) {  // 각 워크스페이스에 10개의 프로필 생성
//                Profile profile = Profile.builder()
//                        .name("User " + i + " in Workspace " + workspaceId)
//                        .nickname("nickname" + i)
//                        .position("Developer")
//                        .status("Active")
//                        .profilePictureUrl("https://example.com/profile" + i + ".png")
//                        .isOnline(false)
//                        .isWorkspaceCreator(i == 1)  // 첫 번째 사용자는 워크스페이스 생성자
//                        .memberId(workspaceId * 100 + i)  // 멤버 ID 예시
//                        .email("user" + i + "@example.com")
//                        .workspaceId(workspaceId)
//                        .build();
//                profileRepository.save(profile);
//            }
//        }
//        System.out.println("Dummy profiles created.");
//    }
//
//    // 더미 프로필 삭제
//    private void deleteDummyProfiles() {
//        profileRepository.deleteAll();  // 모든 프로필 삭제
//        System.out.println("Dummy profiles deleted.");
//    }
//}
