package com.bdool.chatservice.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.bdool.chatservice.model.Enum.EntityType;
import org.springframework.beans.factory.annotation.Value;
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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final FileRepository fileRepository;
    private final AmazonS3 naverCloud;
    private final String bucketName;

    @Autowired
    public FileStorageServiceImpl(FileRepository fileRepository, AmazonS3 naverCloud, @Value("${ncloud.s3.bucket}") String bucketName) {
        this.fileRepository = fileRepository;
        this.naverCloud = naverCloud;
        this.bucketName = bucketName;
    }

    @Override
    public List<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }

    @Override
    public FileEntity storeFile(MultipartFile file, Long profileImgId, UUID channelImgId, Long workspacesImgId, UUID messageImgId, EntityType entityType) {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (originalFileName.contains("..")) {
            throw new FileStorageException("Invalid path sequence " + originalFileName);
        }

        // UUID를 파일명 앞에 붙여 중복을 방지
        String fileName = UUID.randomUUID().toString() + "_" + originalFileName;

        // 파일 업로드
        try (InputStream inputStream = file.getInputStream()) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            // 서버 측 암호화 설정 (AES-256)
            metadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);

            // PutObjectRequest에 ACL 설정 추가
            naverCloud.putObject(new PutObjectRequest(bucketName, fileName, inputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));  // ACL 설정
        } catch (IOException | SdkClientException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }

        // 파일 확장자 처리
        String extension = Optional.ofNullable(originalFileName)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(originalFileName.lastIndexOf(".") + 1))
                .orElse("");

        // Null 검증을 통해 ID를 Map에 넣기
        Map<EntityType, Object> entityIdMap = new HashMap<>();
        if (profileImgId != null) entityIdMap.put(EntityType.PROFILE, profileImgId);
        if (channelImgId != null) entityIdMap.put(EntityType.CHANNEL, channelImgId);
        if (workspacesImgId != null) entityIdMap.put(EntityType.WORKSPACE, workspacesImgId);
        if (messageImgId != null) entityIdMap.put(EntityType.MESSAGE, messageImgId);

        Object entityId = entityIdMap.get(entityType);
        if (entityId == null) {
            throw new FileStorageException("ID cannot be null for entity type " + entityType);
        }

        // 파일 URL 가져오기
        String fileUrl = naverCloud.getUrl(bucketName, fileName).toString();
        FileEntity.FileEntityBuilder fileEntityBuilder = FileEntity.builder()
                .fileId(UUID.randomUUID())
                .fname(fileName)  // UUID가 추가된 새로운 파일명 사용
                .path(fileUrl)
                .extension(extension)
                .size((int) file.getSize())
                .uploadedAt(LocalDateTime.now())
                .entityType(entityType);

        // EntityType에 따른 ID 설정
        if (entityType == EntityType.PROFILE) {
            fileEntityBuilder.profileImgId((Long) entityId);
        } else if (entityType == EntityType.CHANNEL) {
            fileEntityBuilder.channelImgId((UUID) entityId);
        } else if (entityType == EntityType.WORKSPACE) {
            fileEntityBuilder.workspacesImgId((Long) entityId);
        } else if (entityType == EntityType.MESSAGE) {
            fileEntityBuilder.messageImgId((UUID) entityId);
        }

        FileEntity fileEntity = fileEntityBuilder.build();

        return fileRepository.save(fileEntity);
    }

    @Override
    public ResponseEntity<Resource> loadFileAsResource(UUID fileId, HttpServletRequest request) {
        FileEntity fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));

        try {
            S3Object s3Object = naverCloud.getObject(bucketName, fileEntity.getFname());
            InputStream inputStream = s3Object.getObjectContent();
            Resource resource = new InputStreamResource(inputStream);

            String contentType = request.getServletContext().getMimeType(fileEntity.getFname());
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            String encodedFileName = URLEncoder.encode(fileEntity.getFname(), StandardCharsets.UTF_8).replace("+", "%20");

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
                    .body(resource);
        } catch (SdkClientException ex) {
            throw new FileStorageException("Error loading file", ex);
        }
    }

    @Override
    public void deleteFile(UUID fileId) {
        FileEntity fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));

        try {
            naverCloud.deleteObject(bucketName, fileEntity.getFname());
            fileRepository.deleteById(fileId);
        } catch (SdkClientException ex) {
            throw new FileStorageException("Could not delete file " + fileEntity.getFname() + " from S3", ex);
        } catch (Exception ex) {
            throw new FileStorageException("Could not delete file record from database", ex);
        }
    }
}
