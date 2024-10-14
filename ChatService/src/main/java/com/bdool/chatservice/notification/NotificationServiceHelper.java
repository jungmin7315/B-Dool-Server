package com.bdool.chatservice.notification;

import com.bdool.chatservice.model.domain.ParticipantModel;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NotificationServiceHelper {

    private static long convertUUIDToLong(UUID uuid) {
        return uuid.getMostSignificantBits() & Long.MAX_VALUE;
    }

    public static NotificationModel createChannelJoinNotification(ParticipantModel participantModel, UUID channelId, Long recipientProfileId, String channelName) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("nickname", participantModel.getNickname());
        metadata.put("channelId", channelId);
        metadata.put("channelName", channelName);

        return NotificationModel.builder()
                .profileId(recipientProfileId)
                .notificationType(NotificationType.CHANNEL_JOIN)
                .message(participantModel.getNickname() + "님이 " + channelName + " 채널에 입장했습니다.")
                .metadata(metadata)
                .targetType(NotificationTargetType.CHANNEL)
                .targetId(convertUUIDToLong(channelId))
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
