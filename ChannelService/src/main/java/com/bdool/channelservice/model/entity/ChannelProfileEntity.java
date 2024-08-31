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

    @EmbeddedId
    private ChannelProfileId id = new ChannelProfileId();

    @Column(name = "joinedAt", nullable = false, updatable = false)
    private Timestamp joinedAt;
}
