package com.bdool.workspaceservice.workspace.model.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkspaceRequest {
    private String name;
    private String description;
    private String workspaceImageUrl;
    private String url;
    private Long ownerId; // 생성자의 멤버 id
}
