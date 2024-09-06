package com.bdool.chatservice.service;

import com.bdool.chatservice.model.domain.ChannelModel;
import com.bdool.chatservice.model.domain.FileModel;
import com.bdool.chatservice.model.entity.ChannelEntity;
import com.bdool.chatservice.model.entity.FileEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface FileService {
    FileEntity save(FileModel file);

    FileEntity update(UUID fileId, FileModel file);

    List<FileEntity> findAll();

    Optional<FileEntity> findById(UUID fileId);

    boolean existsById(UUID fileId);

    long count();

    void deleteById(UUID fileId);
}
