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
@Document(collection = "messageReadStatus")
public class MessageReadStatusEntity {

    @Id
    private UUID id;
    private Long profileId;    // 사용자 ID
    private UUID channelId; // 채팅방 ID
    private UUID lastReadMessageId; // 마지막으로 읽은 메시지 ID
}

