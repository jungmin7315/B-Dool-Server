package com.bdool.bdool.workspace.service;
import com.bdool.bdool.workspace.model.WorkspaceEntity;
import com.bdool.bdool.workspace.model.domain.WorkspaceRequest;

import java.util.List;
import java.util.Optional;

public interface WorkspaceService {
    WorkspaceEntity createWorkspace(WorkspaceRequest request);
    List<WorkspaceEntity> getWorkspaces();
    Optional<WorkspaceEntity> getWorkspaceById(Long id);
    WorkspaceEntity updateWorkspace(Long workspaceId, WorkspaceRequest request);
    boolean isUrlAvailable(String url); // url 더블체크
    void deleteWorkspace(Long workspaceId, Long userId);
    long countWorkspace();
    List<WorkspaceEntity> getWorkspacesByIds(List<Long> workspaceIds);

}
