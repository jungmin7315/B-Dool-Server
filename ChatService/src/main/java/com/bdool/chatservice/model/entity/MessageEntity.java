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
    private UUID messageId;

    private UUID channelId;

    private String content;

    private LocalDateTime sendDate;

    private Boolean isEdited;

    private Boolean isDeleted;

    private UUID parentMessageId;
    private UUID memberId;
}