package com.bdool.messageservice.service.impl;

import com.bdool.messageservice.model.domain.MessageFileModel;
import com.bdool.messageservice.model.entity.MessageFileEntity;
import com.bdool.messageservice.model.repository.MessageFileRepository;
import com.bdool.messageservice.service.MessageFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageFileServiceImpl implements MessageFileService {

    private final MessageFileRepository messageFileRepository;

    @Override
    public MessageFileEntity save(MessageFileModel messageFileModel) {
        return messageFileRepository.save(
                MessageFileEntity.builder()
                        .fileId(messageFileModel.getFileId())
                        .messageId(messageFileModel.getMessageId())
                        .build()
        );
    }

    @Override
    public List<MessageFileEntity> findAll() {
        return messageFileRepository.findAll();
    }

    @Override
    public Optional<MessageFileEntity> findById(Long messageFileId) {
        return messageFileRepository.findById(messageFileId);
    }

    @Override
    public boolean existsById(Long messageFileId) {
        return messageFileRepository.existsById(messageFileId);
    }

    @Override
    public long count() {
        return messageFileRepository.count();
    }

    @Override
    public void deleteById(Long messageFileId) {
        messageFileRepository.deleteById(messageFileId);
    }
}
