package com.bdool.memberhubservice.member.domain.profile.entity.model.sse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileOnlineResponse {

    private Long id;
    private Long workspaceId;
    private Boolean isOnline;
}
