package com.bdool.memberhubservice.notification.domain.setting.entity;

import com.bdool.memberhubservice.notification.domain.notification.entity.NotificationType;
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

    private NotificationType type;
    private boolean enabled;
    private Long profileId;

    public void updateEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
