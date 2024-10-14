package com.bdool.chatservice.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "userChannelStatus")
public class UserChannelStatusEntity {

    @Id
    private UUID id;

    private UUID channelId;

    private UUID userId;

    private int unreadCount;  // 읽지 않은 메시지 수
}
