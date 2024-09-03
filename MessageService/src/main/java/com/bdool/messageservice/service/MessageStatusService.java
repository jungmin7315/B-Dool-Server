package com.bdool.messageservice.service;

import com.bdool.messageservice.model.domain.MessageFileModel;
import com.bdool.messageservice.model.domain.MessageStatusModel;
import com.bdool.messageservice.model.entity.MessageFileEntity;
import com.bdool.messageservice.model.entity.MessageStatusEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MessageStatusService {
    MessageStatusEntity save(MessageStatusModel messageStatus);
    List<MessageStatusEntity> findAll();
    Optional<MessageStatusEntity> findById(Long messageStatusId);

    boolean existsById(Long messageStatusId);

    long count();

    void deleteById(Long messageStatusId);
}
