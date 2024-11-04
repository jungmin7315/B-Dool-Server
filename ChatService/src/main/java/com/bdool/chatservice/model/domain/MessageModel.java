package com.bdool.chatservice.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Data
public class MessageModel {
    private UUID messageId;

    private UUID channelId;

    private String content;

    private LocalDateTime sendDate;

    private Boolean isEdited;

    private Boolean isDeleted;

    private UUID parentMessageId;

    private Long profileId;

    private String fileURL;
}
