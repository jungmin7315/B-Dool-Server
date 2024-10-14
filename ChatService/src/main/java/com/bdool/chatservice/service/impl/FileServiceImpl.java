package com.bdool.chatservice.service.impl;

import com.bdool.chatservice.model.entity.FileEntity;
import com.bdool.chatservice.service.FileService;
import com.bdool.chatservice.service.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileStorageService fileStorageService;

    @Override
    public FileEntity uploadFile(MultipartFile file, UUID profileId) {
        return fileStorageService.storeFile(file, profileId);
    }

    @Override
    public ResponseEntity<Resource> downloadFile(UUID fileId, HttpServletRequest request) {
        return fileStorageService.loadFileAsResource(fileId, request);
    }

    @Override
    public void deleteFile(UUID fileId) {
        fileStorageService.deleteFile(fileId);
    }
}
