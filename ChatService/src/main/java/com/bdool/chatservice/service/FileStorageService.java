package com.bdool.chatservice.service;

import com.bdool.chatservice.model.entity.FileEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface FileStorageService {
    List<FileEntity> getAllFiles();
    FileEntity storeFile(MultipartFile file, Long profileId);
    ResponseEntity<Resource> loadFileAsResource(UUID fileId, HttpServletRequest request);
    void deleteFile(UUID fileId);
}

