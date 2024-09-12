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
    private UUID channelId; // MongoDB에서는 @GeneratedValue를 사용하지 않고, 수동으로 UUID를 생성할 수 있습니다.

    private UUID workspacesId;

    private String name;

    private Boolean isPrivate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String description;

    private UUID profileId;

    private ChannelType channelType; // MongoDB에서는 @Enumerated 대신 일반 필드로 처리됩니다.
}
