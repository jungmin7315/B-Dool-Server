package com.bdool.chatservice.service;

import com.bdool.chatservice.model.entity.FileEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public interface FileService {

    FileEntity uploadFile(MultipartFile file, UUID profileId);
    ResponseEntity<Resource> downloadFile(UUID fileId, HttpServletRequest request);
    void deleteFile(UUID fileId);
}
