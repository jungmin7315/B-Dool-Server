package com.bdool.chatservice.model.entity;

import com.bdool.chatservice.model.Enum.ChannelType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "channel")
public class ChannelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "channelId", updatable = false, nullable = false)
    private UUID channelId;

    @Column(name = "workspacesId", nullable = false)
    private Long workspacesId;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "isPrivate", nullable = false)
    private Boolean isPrivate;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @Column(name = "description")
    private String description;

    @Column(name = "profileId", nullable = false)
    private UUID profileId;

    @Enumerated(EnumType.STRING)
    @Column(name = "channelType", nullable = false)
    private ChannelType channelType;

}
