package com.bdool.bdool.workspace.service;

import com.bdool.bdool.workspace.model.WorkspaceEntity;
import com.bdool.bdool.workspace.model.WorkspaceRepository;
import com.bdool.bdool.workspace.model.domain.WorkspaceRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    @Override
    @Transactional
    public WorkspaceEntity createWorkspace(WorkspaceRequest request) {
        WorkspaceEntity Workspace = WorkspaceEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .workspaceImageUrl(request.getWorkspaceImageUrl())
                .ownerId(request.getOwnerId())
                .url(createUniqueUrl(request.getName()))
                .build();

        return workspaceRepository.save(Workspace);
    }

    // 워크스페이스 URL 생성
    private String createUniqueUrl(String workspaceName) {
        String baseUrl = workspaceName.toLowerCase().replace(" ", "-");
        String uniqueUrl = baseUrl;
        int counter = 1;

        // URL의 유일성을 보장하기 위해 중복 체크
        while (workspaceRepository.existsByUrl(uniqueUrl)) {
            uniqueUrl = baseUrl + "-" + counter++;
        }

        return uniqueUrl;
    }

    @Override
    public List<WorkspaceEntity> getWorkspaces() {
        return workspaceRepository.findAll();
    }

    @Override
    public Optional<WorkspaceEntity> getWorkspaceById(Long id) {
        return workspaceRepository.findById(id);
    }

    @Override
    @Transactional
    public WorkspaceEntity updateWorkspace(Long workspaceId, WorkspaceRequest request) {
        // 1. 워크스페이스 조회
        WorkspaceEntity workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new RuntimeException("워크스페이스를 찾을 수 없습니다."));

        // 2. 사용자가 워크스페이스 생성자인지 확인
        if (!workspace.getOwnerId().equals(request.getOwnerId())) {
            throw new RuntimeException("워크스페이스 생성자만 정보를 수정할 수 있습니다.");
        }

        // 3. URL 중복 체크 (if URL이 변경된 경우)
        if (!workspace.getUrl().equals(request.getUrl())) {
            if (workspaceRepository.existsByUrl(request.getUrl())) {
                throw new RuntimeException("URL이 이미 사용 중입니다.");
            }
        }

        // 4. 워크스페이스 수정
        WorkspaceEntity updatedWorkspace = WorkspaceEntity.builder()
                .id(workspace.getId())
                .name(request.getName())
                .description(request.getDescription())
                .workspaceImageUrl(request.getWorkspaceImageUrl())
                .ownerId(workspace.getOwnerId())
                .url(request.getUrl())
                .createdAt(workspace.getCreatedAt())
                .build();

        return workspaceRepository.save(updatedWorkspace);
    }

    @Override
    public boolean isUrlAvailable(String url) {
        return !workspaceRepository.existsByUrl(url);
    }

    @Override
    @Transactional
    public void deleteWorkspace(Long workspaceId, Long userId) {
        // 1. 워크스페이스 조회
        WorkspaceEntity workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new RuntimeException("워크스페이스를 찾을 수 없습니다."));

        // 2. 사용자가 워크스페이스 생성자인지 확인
        if (!workspace.getOwnerId().equals(userId)) {
            throw new RuntimeException("워크스페이스 생성자만 삭제할 수 있습니다.");
        }

        // 3. 워크스페이스 삭제
        workspaceRepository.delete(workspace);
    }

    @Override
    public long countWorkspace() {
        return workspaceRepository.count();
    }

    @Override
    public List<WorkspaceEntity> getWorkspacesByIds(List<Long> workspaceIds) {
        return workspaceRepository.findByIdIn(workspaceIds);
    }

}
