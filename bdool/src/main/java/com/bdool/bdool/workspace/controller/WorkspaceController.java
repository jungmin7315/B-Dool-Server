package com.bdool.bdool.workspace.controller;


import com.bdool.bdool.workspace.model.WorkspaceEntity;
import com.bdool.bdool.workspace.model.domain.WorkspaceRequest;
import com.bdool.bdool.workspace.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @PostMapping("/new-workspace")
    public ResponseEntity<WorkspaceEntity> createWorkspace(@RequestBody WorkspaceRequest request) {
        WorkspaceEntity createdWorkspace = workspaceService.createWorkspace(request);
        return ResponseEntity.ok(createdWorkspace);
    }

    @GetMapping("/list") //All
    public ResponseEntity<List<WorkspaceEntity>> getAllWorkspaces() {
        List<WorkspaceEntity> workspaces = workspaceService.getWorkspaces();
        return ResponseEntity.ok(workspaces);
    }

    @GetMapping("/{workspaceId}") //one
    public ResponseEntity<WorkspaceEntity> getWorkspaceById(@PathVariable Long id) {
        Optional<WorkspaceEntity> workspaceOpt = workspaceService.getWorkspaceById(id);
        return workspaceOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 워크스페이스 수정
    @PutMapping("/{workspaceId}")
    public ResponseEntity<WorkspaceEntity> updateWorkspace(@PathVariable Long workspaceId, @RequestBody WorkspaceRequest request) {
        WorkspaceEntity updatedWorkspace = workspaceService.updateWorkspace(workspaceId, request);
        return ResponseEntity.ok(updatedWorkspace);
    }

    // URL 중복 체크
    @GetMapping("/check-url")
    public ResponseEntity<Boolean> checkUrlAvailability(@RequestBody String url) {
        boolean isAvailable = workspaceService.isUrlAvailable(url);
        return ResponseEntity.ok(isAvailable);
    }

    //삭제
    @DeleteMapping("/{workspaceId}")
    public ResponseEntity<Void> deleteWorkspace(@PathVariable Long workspaceId, @RequestBody Long userId) {
        workspaceService.deleteWorkspace(workspaceId, userId);
        return ResponseEntity.noContent().build();
    }
}
