package com.bdool.messageservice.model.domain;

import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Data
public class MessageFileModel {
    private Long messageFileId;

    private UUID messageId;

    private UUID fileId;
}
