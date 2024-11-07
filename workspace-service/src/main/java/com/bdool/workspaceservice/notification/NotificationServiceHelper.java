package com.bdool.workspaceservice.notification;

import com.bdool.workspaceservice.calendar.model.entity.AttendeeEntity;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NotificationServiceHelper {

    public static NotificationModel createEventJoinNotification(AttendeeEntity attendee, Long recipientProfileId) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("nickname", attendee.getNickname());
        metadata.put("eventId", attendee.getEvent().getId());
        metadata.put("eventTitle", attendee.getEvent().getTitle());

        return NotificationModel.builder()
                .profileId(recipientProfileId)
                .notificationType(NotificationType.EVENT_INVITE)
                .message(attendee.getNickname() + "님 " + attendee.getEvent().getTitle() + " 일정에 초대되었습니다.")
                .metadata(metadata)
                .targetType(NotificationTargetType.EVENT)
                .targetId(attendee.getEvent().getId())
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
