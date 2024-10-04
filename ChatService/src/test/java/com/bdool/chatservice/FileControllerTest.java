package com.bdool.chatservice;

import com.bdool.chatservice.controller.FileController;
import com.bdool.chatservice.model.Enum.EntityType;
import com.bdool.chatservice.model.entity.FileEntity;
import com.bdool.chatservice.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FileControllerTest {

    @Mock
    private FileService fileService;

    @InjectMocks
    private FileController fileController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(fileController).build();
    }

    @Test
    public void testUploadFile() throws Exception {
        // 가짜 파일 및 요청 파라미터 설정
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.txt", "text/plain", "Test file content".getBytes());

        // 가짜 FileEntity 객체를 Builder로 생성
        FileEntity fakeFileEntity = FileEntity.builder()
                .fileId(UUID.randomUUID())
                .fname("test.txt")
                .path("/files/test.txt")
                .size(1024)
                .extension("txt")
                .uploadedAt(null)  // 필요한 경우 null 또는 원하는 값을 설정
                .entityType(EntityType.PROFILE)
                .build();

        when(fileService.uploadFile(any(), any(), any(), any(), any(), any())).thenReturn(fakeFileEntity);

        // 실제 HTTP 요청 테스트
        mockMvc.perform(multipart("/api/files/upload")
                        .file(mockFile)
                        .param("profileId", "1") // profileId가 있을 때
                        .param("entityType", EntityType.PROFILE.name()) // EntityType 필수
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());
    }

    @Test
    public void testUploadFileWithNullValues() throws Exception {
        // 가짜 파일 및 요청 파라미터 설정
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.txt", "text/plain", "Test file content".getBytes());

        // 가짜 FileEntity 객체를 Builder로 생성
        FileEntity fakeFileEntity = FileEntity.builder()
                .fileId(UUID.randomUUID())
                .fname("test.txt")
                .path("/files/test.txt")
                .size(1024)
                .extension("txt")
                .uploadedAt(null)
                .entityType(EntityType.MESSAGE)
                .build();

        when(fileService.uploadFile(any(), any(), any(), any(), any(), any())).thenReturn(fakeFileEntity);

        // 실제 HTTP 요청 테스트 (ID 필드 중 일부는 null 값으로 처리)
        mockMvc.perform(multipart("/api/files/upload")
                        .file(mockFile)
                        .param("entityType", EntityType.MESSAGE.name()) // EntityType 필수
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());
    }
}
