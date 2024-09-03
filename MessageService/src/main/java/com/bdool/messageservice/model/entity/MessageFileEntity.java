package com.bdool.messageservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "messageFile")
public class MessageFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "messageFileId", updatable = false, nullable = false)
    private Long messageFileId;

    @Column(name = "messageId", nullable = false)
    private UUID messageId;

    @Column(name = "fileId", nullable = false)
    private UUID fileId;
}
