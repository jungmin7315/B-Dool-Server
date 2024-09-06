package com.bdool.chatservice.model.entity;

import com.bdool.chatservice.model.Enum.SessionType;
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
@Document(collection = "session")
public class SessionEntity {

    @Id
    private UUID sessionId;  // MongoDB에서는 @GeneratedValue가 필요하지 않습니다.

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    private Long profileId;

    private SessionType sessionType;  // MongoDB에서는 @Enumerated 없이 Enum 값이 저장됩니다.

    private UUID channelMemberId;
}
