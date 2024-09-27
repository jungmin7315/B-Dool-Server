package com.bdool.chatservice.service.impl;

import org.springframework.beans.factory.annotation.Value;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.SdkClientException;
import com.bdool.chatservice.exception.FileStorageException;
import com.bdool.chatservice.exception.MyFileNotFoundException;
import com.bdool.chatservice.model.entity.FileEntity;
import com.bdool.chatservice.model.repository.FileRepository;
import com.bdool.chatservice.service.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final FileRepository fileRepository;
    private final AmazonS3 amazonS3;
    private final String bucketName;

    @Autowired
    public FileStorageServiceImpl(FileRepository fileRepository, AmazonS3 amazonS3, @Value("${ncloud.s3.bucket}") String bucketName) {
        this.fileRepository = fileRepository;
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
    }

    @Override
    public List<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }

    @Override
    public FileEntity storeFile(MultipartFile file, UUID profileId) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            throw new FileStorageException("Invalid path sequence " + fileName);
        }

        try (InputStream inputStream = file.getInputStream()) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, inputStream, metadata));
        } catch (IOException | SdkClientException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }

        String extension = "";
        int index = fileName.lastIndexOf(".");
        if (index > 0) {
            extension = fileName.substring(index + 1);
        }

        FileEntity fileEntity = FileEntity.builder()
                .fileId(UUID.randomUUID())
                .fname(fileName)
                .path("https://" + bucketName + ".ncloudstorage.com/" + fileName)
                .extension(extension)
                .size((int) file.getSize())
                .uploadedAt(LocalDateTime.now())
                .profileId(profileId)
                .build();

        return fileRepository.save(fileEntity);
    }

    @Override
    public ResponseEntity<Resource> loadFileAsResource(UUID fileId, HttpServletRequest request) {
        FileEntity fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));

        try {
            S3Object s3Object = amazonS3.getObject(bucketName, fileEntity.getFname());
            InputStream inputStream = s3Object.getObjectContent();
            Resource resource = new InputStreamResource(inputStream);

            String contentType = request.getServletContext().getMimeType(fileEntity.getFname());
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            String encodedFileName = URLEncoder.encode(fileEntity.getFname(), "UTF-8").replace("+", "%20");

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
                    .body(resource);
        } catch (SdkClientException  | UnsupportedEncodingException ex) {
            throw new FileStorageException("Error loading file", ex);
        }
    }

    @Override
    public void deleteFile(UUID fileId) {
        FileEntity fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
        try {
            amazonS3.deleteObject(bucketName, fileEntity.getFname());
            fileRepository.deleteById(fileId);
        } catch (SdkClientException ex) {
            throw new FileStorageException("Could not delete file " + fileEntity.getFname(), ex);
        }
    }
}
