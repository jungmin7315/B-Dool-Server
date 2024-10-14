package com.bdool.chatservice;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bdool.chatservice.exception.FileStorageException;
import com.bdool.chatservice.model.Enum.EntityType;
import com.bdool.chatservice.model.entity.FileEntity;
import com.bdool.chatservice.model.repository.FileRepository;
import com.bdool.chatservice.service.impl.FileStorageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FileStorageServiceImplTest {

    @Mock
    private FileRepository fileRepository;

    @Mock
    private AmazonS3 amazonS3;

    @InjectMocks
    private FileStorageServiceImpl fileStorageService;

    private MockMultipartFile mockMultipartFile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        // 가짜 파일 생성
        mockMultipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "Test file content".getBytes());
    }

    @Test
    public void testStoreFileWithProfileEntityType() {
        // Arrange
        Long profileId = 1L;
        UUID channelImgId = null;
        Long workspacesImgId = null;
        UUID messageImgId = null;
        EntityType entityType = EntityType.PROFILE;

        // AmazonS3 putObject가 정상적으로 호출되는지 확인하기 위해 설정
        when(amazonS3.putObject(any(PutObjectRequest.class))).thenReturn(null); // doNothing() 대신 사용

        // FileEntity 저장 시 가짜 리턴값 설정
        FileEntity fakeFileEntity = FileEntity.builder()
                .fileId(UUID.randomUUID())
                .fname("test.txt")
                .path("/files/test.txt")
                .size(1024)
                .extension("txt")
                .uploadedAt(null)  // 필요한 경우 null 또는 원하는 값을 설정
                .profileImgId(profileId)
                .entityType(entityType)
                .build();

        when(fileRepository.save(any(FileEntity.class))).thenReturn(fakeFileEntity);

        // Act
        FileEntity result = fileStorageService.storeFile(mockMultipartFile, profileId, channelImgId, workspacesImgId, messageImgId, entityType);

        // Assert
        assertNotNull(result);
        verify(amazonS3, times(1)).putObject(any(PutObjectRequest.class));
        verify(fileRepository, times(1)).save(any(FileEntity.class));
    }

    @Test
    public void testStoreFileWithInvalidFileName() {
        // Arrange
        MockMultipartFile invalidFile = new MockMultipartFile("file", "../invalid.txt", "text/plain", "Invalid file content".getBytes());
        Long profileId = 1L;
        UUID channelImgId = null;
        Long workspacesImgId = null;
        UUID messageImgId = null;
        EntityType entityType = EntityType.PROFILE;

        // Act & Assert
        assertThrows(FileStorageException.class, () -> {
            fileStorageService.storeFile(invalidFile, profileId, channelImgId, workspacesImgId, messageImgId, entityType);
        });
    }
}
