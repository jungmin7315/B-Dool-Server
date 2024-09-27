package com.bdool.chatservice.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
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

    private UUID messageId;  // 어떤 메시지인지
    private UUID profileId;     // 메시지를 읽은 사용자 ID
    private LocalDateTime readAt; // 읽은 시간
}

