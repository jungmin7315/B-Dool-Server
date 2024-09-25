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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Document(collection = "member")
public class ParticipantEntity {

    @Id
    private UUID participantId;// MongoDB에서는 @GeneratedValue가 필요하지 않습니다.

    private UUID channelId;

    private String profileName;

    private boolean favorited;

    private LocalDateTime joinedAt;
}
