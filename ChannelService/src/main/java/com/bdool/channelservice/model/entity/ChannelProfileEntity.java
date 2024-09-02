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
@Table(name = "channelProfile")
public class ChannelProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chaneelProfileId;

    @Column(name = "profileId")
    private Long profileId;

    @Column(name = "channelId")
    private Long channelId;

    @Column(name = "joinedAt", nullable = false, updatable = false)
    private Timestamp joinedAt;
}
