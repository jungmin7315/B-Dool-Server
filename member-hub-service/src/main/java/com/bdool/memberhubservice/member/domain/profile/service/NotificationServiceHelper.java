package com.bdool.memberhubservice.member.domain.profile.service;

import com.bdool.memberhubservice.member.domain.profile.entity.model.ProfileModel;
import com.bdool.memberhubservice.notification.NotificationModel;
import com.bdool.memberhubservice.notification.NotificationTargetType;
import com.bdool.memberhubservice.notification.NotificationType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

public class NotificationServiceHelper {

    public static NotificationModel createNotificationModel(ProfileModel profileModel, Long workspaceId, Long recipientProfileId) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("nickname", profileModel.getNickname());
        metadata.put("workspaceId", workspaceId);

        return NotificationModel.builder()
                .profileId(recipientProfileId)
                .notificationType(NotificationType.WORKSPACE_ENTRY)
                .message(profileModel.getNickname() + "님이 워크스페이스에 입장했습니다.")
                .metadata(metadata)
                .targetType(NotificationTargetType.WORKSPACE)
                .targetId(workspaceId)
                .build();
    }

    public static void sendNotification(WebClient webClient, String notificationServiceUrl, NotificationModel notificationModel) {
        webClient.post()
                .uri(notificationServiceUrl + "/notifications")
                .bodyValue(notificationModel)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(response -> System.out.println("알림 전송 성공"))
                .doOnError(error -> System.err.println("알림 전송 실패: " + error.getMessage()))
                .subscribe();
    }
}
