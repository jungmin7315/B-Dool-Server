package com.bdool.chatservice.controller;

import com.bdool.chatservice.model.domain.FileModel;
import com.bdool.chatservice.model.entity.FileEntity;
import com.bdool.chatservice.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController{

    private final FileService fileService;

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody  FileModel file) {
        return ResponseEntity.ok(fileService.save(file));
    }

    @PutMapping("/{fileId}")
    public ResponseEntity<?> update(@PathVariable UUID fileId, @RequestBody FileModel file) {
        return ResponseEntity.ok(fileService.update(fileId, file));
    }

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        List<FileEntity> files = fileService.findAll();
        if(files.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(files);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<?> findById(@PathVariable UUID fileId) {
        return fileService.findById(fileId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/exists/{fileId}")
    public ResponseEntity<?> existsById(@PathVariable UUID fileId) {
        return ResponseEntity.ok(fileService.existsById(fileId));
    }

    @GetMapping("/count")
    public ResponseEntity<?> count() {
        return ResponseEntity.ok(fileService.count());
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<?> deleteById(@PathVariable UUID fileId) {
        fileService.deleteById(fileId);
        return ResponseEntity.noContent().build();
    }
}
