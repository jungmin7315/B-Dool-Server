package com.bdool.bdool.workspace.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkspaceRepository extends JpaRepository<WorkspaceEntity, Long> {
    boolean existsByUrl(String url);
    List<WorkspaceEntity> findByIdIn(List<Long> ids);
}