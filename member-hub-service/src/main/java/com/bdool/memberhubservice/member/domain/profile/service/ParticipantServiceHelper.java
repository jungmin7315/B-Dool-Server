package com.bdool.memberhubservice.member.domain.profile.service;

import org.springframework.web.reactive.function.client.WebClient;

public class ParticipantServiceHelper {

    public static void sendOnlineStatusToChannelService(WebClient webClient,
                                                        String channelServiceUrl,
                                                        Long profileId,
                                                        Boolean isOnline) {
        webClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path(channelServiceUrl + "/participants/" + profileId + "/online")
                        .queryParam("isOnline", isOnline)
                        .build())
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(response -> System.out.println("채널 서비스에 온라인 상태 변경 요청 성공"))
                .doOnError(error -> System.err.println("채널 서비스에 온라인 상태 변경 요청 실패: " + error.getMessage()))
                .subscribe();
    }

    public static void sendNicknameToChannelService(WebClient webClient,
                                                    String channelServiceUrl,
                                                    Long profileId,
                                                    String nickname) {
        webClient.patch()
                .uri(uriBuilder -> uriBuilder
                        .path(channelServiceUrl + "/participants/" + profileId + "/nickname")
                        .queryParam("nickname", nickname)
                        .build())
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(response -> System.out.println("채널 서비스에 닉네임 상태 변경 요청 성공"))
                .doOnError(error -> System.err.println("채널 서비스에 닉네임 상태 변경 요청 실패: " + error.getMessage()))
                .subscribe();
    }
}
