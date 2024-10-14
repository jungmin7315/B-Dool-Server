package com.bdool.notificationservice.notification.domain.notification.service.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.springframework.stereotype.Service;
import com.google.firebase.messaging.Notification;

@Service
public class FCMService {

    // FCM 푸시 알림 전송
    public void sendNotification(String token, String title, String body) {
        try {
            Notification notification = Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build();

            Message message = Message.builder()
                    .setToken(token)  // FCM 토큰
                    .setNotification(notification)  // 알림 설정
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}