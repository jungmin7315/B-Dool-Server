package com.bdool.chatservice.model.entity;

import com.bdool.chatservice.model.Enum.ChannelType;
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
@Document(collection = "channel")
public class ChannelEntity {

    @Id
    private UUID channelId; //UUID 자동 생성

    private Long workspacesId; // 받아야 하는 필드

    private String name; // 받아야 하는 필드

    private Boolean isPrivate; // 받아야 하는 필드

    private LocalDateTime createdAt; // LocalDateTime.now()로 처리

    private LocalDateTime updatedAt; // LocalDateTime.now()로 처리

    private String description; // 받아야 하는 필드

    private Long profileId; // 받아야 하는 필드

    private ChannelType channelType; // 받아야 하는 필드
}
