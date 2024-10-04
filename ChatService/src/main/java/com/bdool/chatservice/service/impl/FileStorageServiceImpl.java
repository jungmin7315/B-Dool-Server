package com.bdool.chatservice.service.impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bdool.chatservice.exception.FileStorageException;
import com.bdool.chatservice.exception.MyFileNotFoundException;
import com.bdool.chatservice.model.Enum.EntityType;
import com.bdool.chatservice.model.entity.FileEntity;
import com.bdool.chatservice.model.repository.FileRepository;
import com.bdool.chatservice.service.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
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
    public FileEntity storeFile(MultipartFile file, Long profileImgId, UUID channelImgId, Long workspacesImgId, UUID messageImgId, EntityType entityType) {
        // 원본 파일 이름을 추출하고 UUID를 추가하여 고유한 파일명 생성
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileName = UUID.randomUUID().toString() + "_" + originalFileName;

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

        // EntityType에 따라 적절한 ID 설정
        FileEntity.FileEntityBuilder fileEntityBuilder = FileEntity.builder()
                .fileId(UUID.randomUUID())
                .fname(fileName)  // 고유한 파일 이름을 설정
                .path(fileName)  // S3 버킷 내의 파일 경로만 저장
                .extension(extension)
                .size((int) file.getSize())
                .uploadedAt(LocalDateTime.now())
                .entityType(entityType);

        switch (entityType) {
            case PROFILE:
                if (profileImgId == null) {
                    throw new FileStorageException("Profile ID is required for PROFILE entity type.");
                }
                fileEntityBuilder.profileImgId(profileImgId);
                break;
            case CHANNEL:
                if (channelImgId == null) {
                    throw new FileStorageException("Channel ID is required for CHANNEL entity type.");
                }
                fileEntityBuilder.channelImgId(channelImgId);
                break;
            case WORKSPACE:
                if (workspacesImgId == null) {
                    throw new FileStorageException("Workspace ID is required for WORKSPACE entity type.");
                }
                fileEntityBuilder.workspacesImgId(workspacesImgId);
                break;
            case MESSAGE:
                if (messageImgId == null) {
                    throw new FileStorageException("Message ID is required for MESSAGE entity type.");
                }
                fileEntityBuilder.messageImgId(messageImgId);
                break;
            default:
                throw new FileStorageException("Invalid entity type " + entityType);
        }

        FileEntity fileEntity = fileEntityBuilder.build();
        return fileRepository.save(fileEntity);
    }

    @Override
    public ResponseEntity<?> loadFileAsResource(UUID fileId, HttpServletRequest request) {
        FileEntity fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));

        try {
            // 프리사인드 URL 생성
            String fileName = fileEntity.getFname();

            // URL 만료 시간 설정 (1시간)
            Date expiration = new Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += 1000 * 60 * 60; // 1시간 유효 기간
            expiration.setTime(expTimeMillis);

            // GeneratePresignedUrlRequest를 사용해 프리사인드 URL을 생성
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName)
                    .withMethod(HttpMethod.GET)
                    .withExpiration(expiration);

            URL presignedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

            // 프리사인드 URL을 JSON 형태로 반환
            return ResponseEntity.ok().body("{\"url\": \"" + presignedUrl.toString() + "\"}");

        } catch (SdkClientException ex) {
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
