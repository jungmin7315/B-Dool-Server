package com.bdool.messageservice.service;

import com.bdool.messageservice.model.domain.MessageFileModel;
import com.bdool.messageservice.model.entity.MessageFileEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MessageFileService {
    MessageFileEntity save(MessageFileModel messageFileModel);
    List<MessageFileEntity> findAll();
    Optional<MessageFileEntity> findById(Long messageFileId);

    boolean existsById(Long messageFileId);

    long count();

    void deleteById(Long messageFileId);
}
