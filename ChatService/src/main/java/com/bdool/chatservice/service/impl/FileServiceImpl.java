package com.bdool.chatservice.service.impl;

import com.bdool.chatservice.model.domain.FileModel;
import com.bdool.chatservice.model.entity.FileEntity;
import com.bdool.chatservice.model.repository.FileRepository;
import com.bdool.chatservice.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Override
    public FileEntity save(FileModel file) {
        return fileRepository.save(FileEntity.builder()
                .fname(file.getFname())
                .path(file.getPath())
                .size(file.getSize())
                .kind(file.getKind())
                .uploadedAt(file.getUploadedAt())
                .channelId(file.getChannelId())
                .profileId(file.getProfileId())
                .workspacesId(file.getWorkspacesId())
                .build());
    }

    @Override
    public FileEntity update(UUID fileId, FileModel file) {
        return fileRepository.findById(fileId).map(existingfile -> {
            existingfile.setFname(file.getFname());
            existingfile.setPath(file.getPath());
            existingfile.setSize(file.getSize());
            existingfile.setKind(file.getKind());
            existingfile.setProfileId(file.getProfileId());
            existingfile.setChannelId(file.getChannelId());
            existingfile.setWorkspacesId(file.getWorkspacesId());
            return fileRepository.save(existingfile);
        }).orElseThrow(() -> new RuntimeException("Channel not found with ID: " + fileId));
    }

    @Override
    public List<FileEntity> findAll() {
        return fileRepository.findAll();
    }

    @Override
    public Optional<FileEntity> findById(UUID fileId) {
        return fileRepository.findById(fileId);
    }

    @Override
    public boolean existsById(UUID fileId) {
        return fileRepository.existsById(fileId);
    }

    @Override
    public long count() {
        return fileRepository.count();
    }

    @Override
    public void deleteById(UUID fileId) {
        fileRepository.deleteById(fileId);
    }
}
