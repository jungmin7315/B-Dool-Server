package com.bdool.memberhubservice.notification.domain.setting.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "settings")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String type;
    private boolean enabled;
    private Long profileId;
}
