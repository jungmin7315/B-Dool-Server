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
@Document(collection = "message")
public class MessageEntity {

    @Id
    private UUID messageId;  // MongoDB에서는 @GeneratedValue가 필요하지 않습니다.

    private UUID memberId;

    private String content;

    private LocalDateTime createdAt;

    private Boolean isEdited;

    private Boolean isDeleted;

    private UUID parentMessageId;
}