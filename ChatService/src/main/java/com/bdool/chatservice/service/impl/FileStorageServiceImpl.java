package com.bdool.chatservice.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.bdool.chatservice.model.Enum.EntityType;
import com.bdool.chatservice.model.Enum.FileStatus;
import com.bdool.chatservice.model.Enum.FileType;
import com.bdool.chatservice.util.MD5Util;
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
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final FileRepository fileRepository;
    private final AmazonS3 naverCloud;
    private final String bucketName;

    private static final List<String> ALLOWED_IMAGE_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif");
    private static final List<String> ALLOWED_VIDEO_EXTENSIONS = Arrays.asList("mp4", "avi", "mov");
    private static final List<String> ALLOWED_AUDIO_EXTENSIONS = Arrays.asList("mp3", "wav", "flac");

    @Autowired
    public FileStorageServiceImpl(FileRepository fileRepository, AmazonS3 naverCloud, @Value("${ncloud.s3.bucket}") String bucketName) {
        this.fileRepository = fileRepository;
        this.naverCloud = naverCloud;
        this.bucketName = bucketName;
    }

    // 파일 확장자가 유효한지 확인하는 메서드
    private boolean isValidExtension(String extension) {
        String lowerCaseExt = extension.toLowerCase();
        return ALLOWED_IMAGE_EXTENSIONS.contains(lowerCaseExt)
                || ALLOWED_VIDEO_EXTENSIONS.contains(lowerCaseExt)
                || ALLOWED_AUDIO_EXTENSIONS.contains(lowerCaseExt);
    }

    // 파일 확장자를 기반으로 파일 타입 결정 (이미지, 비디오, 오디오)
    private FileType determineFileType(String extension) {
        if (ALLOWED_IMAGE_EXTENSIONS.contains(extension.toLowerCase())) {
            return FileType.IMAGE;
        } else if (ALLOWED_VIDEO_EXTENSIONS.contains(extension.toLowerCase())) {
            return FileType.VIDEO;
        } else if (ALLOWED_AUDIO_EXTENSIONS.contains(extension.toLowerCase())) {
            return FileType.AUDIO;
        }
        throw new FileStorageException("Invalid file type for extension: " + extension);
    }

    @Override
    public List<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }

    @Override
    public FileEntity storeFile(MultipartFile file, EntityType entityType) {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (originalFileName.contains("..")) {
            throw new FileStorageException("Invalid path sequence " + originalFileName);
        }

        // 파일 확장자 처리
        String extension = Optional.ofNullable(originalFileName)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(originalFileName.lastIndexOf(".") + 1))
                .orElse("");

        // 파일 확장자 유효성 검사
        if (!isValidExtension(extension)) {
            throw new FileStorageException("Invalid file extension: " + extension + ". Only image, video, and audio files are allowed.");
        }

        // 파일 타입 결정
        FileType fileType = determineFileType(extension);

        // UUID를 파일명 앞에 붙여 중복 방지
        String fileName = UUID.randomUUID().toString() + "_" + originalFileName;

        String md5Hash;
        try (InputStream inputStream = file.getInputStream()) {
            // 입력 스트림을 복사하여 하나는 MD5 해시 계산에 사용하고, 하나는 S3 업로드에 사용
            InputStream inputStreamForHash = file.getInputStream();
            InputStream inputStreamForUpload = file.getInputStream();

            // MD5 해시 계산
            md5Hash = MD5Util.calculateMd5Hash(inputStreamForHash);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            // 서버 측 암호화 설정 (AES-256)
            metadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);

            // 파일을 S3에 저장하고 공개 설정
            naverCloud.putObject(new PutObjectRequest(bucketName, fileName, inputStreamForUpload, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));  // ACL 설정
        } catch (SdkClientException | NoSuchAlgorithmException | IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }

        // 파일 URL 생성
        String fileUrl = naverCloud.getUrl(bucketName, fileName).toString();

        // 파일 엔티티 생성
        FileEntity fileEntity = FileEntity.builder()
                .fileId(UUID.randomUUID())
                .fname(fileName)  // UUID가 추가된 새로운 파일명 사용
                .originalFileName(originalFileName)
                .path(fileUrl)
                .extension(extension)
                .size((int) file.getSize())
                .uploadedAt(LocalDateTime.now())
                .entityType(entityType)  // 엔터티 타입 설정
                .status(FileStatus.ACTIVE)  // 기본 상태는 ACTIVE로 설정
                .fileType(fileType)// 파일 타입 설정
                .md5Hash(md5Hash) // MDS 해시 저장
                .build();

        // 파일 엔티티 저장
        return fileRepository.save(fileEntity);
    }

    @Override
    public FileEntity updateFile(UUID fileId, MultipartFile newFile) {
        // 기존 파일 엔티티 조회
        FileEntity existingFileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));

        // 기존 파일이 삭제된 상태라면 업데이트할 수 없도록 예외 처리
        if (existingFileEntity.getStatus() == FileStatus.DELETED) {
            throw new FileStorageException("Cannot update a file that has been deleted. File id: " + fileId);
        }

        // 새로운 파일명 생성
        String originalFileName = StringUtils.cleanPath(newFile.getOriginalFilename());
        String extension = Optional.ofNullable(originalFileName)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(originalFileName.lastIndexOf(".") + 1))
                .orElse("");

        // 파일 확장자 유효성 검사
        if (!isValidExtension(extension)) {
            throw new FileStorageException("Invalid file extension: " + extension + ". Only image, video, and audio files are allowed.");
        }

        // 파일 타입 결정
        FileType fileType = determineFileType(extension);
        String newFileName = UUID.randomUUID().toString() + "_" + originalFileName;

        String md5Hash;
        try (InputStream inputStream = newFile.getInputStream()) {
            // MD5 해시 계산
            InputStream inputStreamForHash = newFile.getInputStream();
            InputStream inputStreamForUpload = newFile.getInputStream();
            md5Hash = MD5Util.calculateMd5Hash(inputStreamForHash);

            // S3에 새 파일 업로드
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(newFile.getSize());
            metadata.setContentType(newFile.getContentType());
            metadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);

            naverCloud.putObject(new PutObjectRequest(bucketName, newFileName, inputStreamForUpload, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            // 기존 파일 S3에서 삭제
            naverCloud.deleteObject(bucketName, existingFileEntity.getFname());

        } catch (SdkClientException | NoSuchAlgorithmException | IOException ex) {
            throw new FileStorageException("Could not update file " + newFileName + ". Please try again!", ex);
        }

        // 업데이트된 파일 URL 생성
        String fileUrl = naverCloud.getUrl(bucketName, newFileName).toString();

        // 기존 파일 엔티티 정보 업데이트
        existingFileEntity.setFname(newFileName); // 새 파일명으로 변경
        existingFileEntity.setOriginalFileName(originalFileName);
        existingFileEntity.setPath(fileUrl);
        existingFileEntity.setExtension(extension);
        existingFileEntity.setSize((int) newFile.getSize());
        existingFileEntity.setUploadedAt(LocalDateTime.now());
        existingFileEntity.setFileType(fileType);
        existingFileEntity.setMd5Hash(md5Hash);

        // 데이터베이스에 업데이트된 엔티티 저장
        return fileRepository.save(existingFileEntity);
    }


    @Override
    public ResponseEntity<Resource> loadFileAsResource(UUID fileId, HttpServletRequest request) {
        FileEntity fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));

        // 파일이 삭제된 상태면 예외 처리
        if (fileEntity.getStatus() == FileStatus.DELETED) {
            throw new MyFileNotFoundException("File has been deleted with id " + fileId);
        }

        try {
            S3Object s3Object = naverCloud.getObject(bucketName, fileEntity.getFname());
            InputStream inputStream = s3Object.getObjectContent();

            String calculatedMd5 = MD5Util.calculateMd5Hash(inputStream);
            if (!calculatedMd5.equals(fileEntity.getMd5Hash())) {
                throw new FileStorageException("File integrity check failed for file id " + fileId);
            }

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
        } catch (SdkClientException | NoSuchAlgorithmException | IOException ex) {
            throw new FileStorageException("Error loading file", ex);
        }
    }

    @Override
    public void deleteFile(UUID fileId) {
        FileEntity fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));

        try {
            // S3에서 실제 파일 삭제
            naverCloud.deleteObject(bucketName, fileEntity.getFname());
        } catch (SdkClientException ex) {
            throw new FileStorageException("Could not delete file " + fileEntity.getFname() + " from S3", ex);
        }

        // 파일 상태를 DELETED로 변경하여 데이터베이스 업데이트
        fileEntity.setStatus(FileStatus.DELETED);
        fileRepository.save(fileEntity);
    }
}