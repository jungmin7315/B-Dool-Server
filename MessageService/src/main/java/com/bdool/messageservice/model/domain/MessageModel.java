package com.bdool.messageservice.model.domain;

import com.bdool.messageservice.model.Enum.MessageType;
import com.bdool.messageservice.model.entity.MessageEntity;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Data
public class MessageModel {
    private UUID mesageId;

    private Long channelId;

    private Long profileId;

    private String content;

    private MessageType messageType;

    private LocalDateTime createdAt;

    private Boolean isEdited = false;

    private Boolean isDeleted = false;

    private MessageEntity parentMessageId;
}
