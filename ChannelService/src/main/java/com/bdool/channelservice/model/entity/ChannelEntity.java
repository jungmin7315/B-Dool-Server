package com.bdool.channelservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "channel")
public class ChannelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "channelId")
    private Long channelId;

    @Column(name = "channelName", length = 50, nullable = false)
    private String channelName;

    @Column(name = "isPrivate", nullable = false)
    private boolean isPrivate = false;

    @Column(name = "createdAt", nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updatedAt", nullable = false, updatable = false)
    private Timestamp  updatedAt;

    @Column(name = "workspaceId", nullable = false)
    private Long workspaceId;

    @Column(name = "profileId", nullable = false)
    private Long profileId;
}