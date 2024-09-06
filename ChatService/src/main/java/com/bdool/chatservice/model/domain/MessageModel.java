package com.bdool.chatservice.model.domain;

import com.bdool.chatservice.model.entity.MessageEntity;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Data
public class MessageModel {
    private UUID messageId;

    private UUID memberId;

    private String content;

    private LocalDateTime createdAt;

    private Boolean isEdited = false;

    private Boolean isDeleted = false;

    private MessageEntity parentMessage;
}
