package com.bdool.channelservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class ChannelProfileId implements Serializable {
    @Column(name = "channelId", nullable = false)
    private Long channelId;

    @Column(name = "profileId", nullable = false)
    private Long profileId;
}
