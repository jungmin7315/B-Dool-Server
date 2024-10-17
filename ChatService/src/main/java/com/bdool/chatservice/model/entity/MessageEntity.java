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
    private UUID messageId; // UUID 자동 생성

    private UUID channelId; // 받아야 하는 필드
    private String content; // 받아야 하는 필드
    private LocalDateTime sendDate; // LocalDateTime.now()로 처리
    private Boolean isEdited; // 처음 생성할 시 false로 들어감
    private Boolean isDeleted; // 처음 생성할 시 false로 들어감
    private UUID parentMessageId; // 값이 없으면 null 있으면 부모의 메시지 ID를 받아야 함
    private Long profileId; // 받아야 하는 필드
    private String fileUrl; // 파일이 첨부된 경우 받아야 함
}