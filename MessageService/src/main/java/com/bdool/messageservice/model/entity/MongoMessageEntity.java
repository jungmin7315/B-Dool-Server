package com.bdool.messageservice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Document(collection = "message")
public class MongoMessageEntity {
    @Id
    private UUID id;
    private String content;
    private Long profileId;
    private Long channelId;
}
