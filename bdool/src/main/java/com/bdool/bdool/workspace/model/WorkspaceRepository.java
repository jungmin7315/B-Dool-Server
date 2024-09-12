package com.bdool.bdool.workspace.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceRepository extends JpaRepository<WorkspaceEntity, Long> {
    boolean existsByUrl(String url);
}