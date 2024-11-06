package com.bdool.chatservice.model.domain;

import com.bdool.chatservice.model.Enum.ChannelType;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Data
public class ChannelModel {

    private UUID channelId; // 생성 시 필요하지 않음, DB에서 자동 생성됨

    private Long workspacesId; // 워크스페이스 ID, 필수 필드

    private String name; // 채널 이름, 필수 필드

    private Boolean isPrivate; // 채널 공개 여부

    private LocalDateTime createdAt; // 자동 처리 필드

    private LocalDateTime updatedAt; // 자동 처리 필드

    private String description; // 채널 설명

    private Long profileId; // 생성자의 프로필 ID, 필수 필드

    private String profileURl; // 생성자의 프로필 URL, 필수 필드

    private String nickname; // 생성자의 닉네임

    private ChannelType channelType; // 채널 타입 (기본, 커스텀, DM 등)

    private Long dmRequestId; // DM 요청 프로필 ID
}
