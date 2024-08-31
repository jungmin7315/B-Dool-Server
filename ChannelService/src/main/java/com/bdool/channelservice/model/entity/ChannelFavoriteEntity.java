package com.bdool.channelservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "channelFavorite")
public class ChannelFavoriteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoriteId;

    @Column(name = "profileId", nullable = false)
    private Long profileId;

    @Column(name = "channelId", nullable = false)
    private Long channelId;

    @Column(name = "favoritedAt", nullable = false, updatable = false)
    private Timestamp favoritedAt;
}
