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
    private UUID participantId; //UUID 자동 생성

    private UUID channelId; // 받아야 하는 필드

    private String nickname;

    private boolean favorited; // 받아야 하는 필드

    private LocalDateTime joinedAt; // LocalDateTime.now()로 처리

    private Boolean isOnline;

    private Long profileId;

    public void updateOnline(Boolean isOnline) {
        this.isOnline = isOnline;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}
