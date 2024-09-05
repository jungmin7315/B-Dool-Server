package com.bdool.messageservice.service.impl;

import com.bdool.messageservice.model.domain.MessageStatusModel;
import com.bdool.messageservice.model.entity.MessageStatusEntity;
import com.bdool.messageservice.model.repository.MessageStatusRepository;
import com.bdool.messageservice.service.MessageStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageStatusServiceImpl implements MessageStatusService {

    private final MessageStatusRepository messageStatusRepository;

    @Override
    public MessageStatusEntity save(MessageStatusModel messageStatus) {
        return messageStatusRepository.save(
                MessageStatusEntity.builder()
                        .isRead(messageStatus.getIsRead())
                        .messageId(messageStatus.getMessageId())
                        .profileId(messageStatus.getProfileId())
                        .readAt(messageStatus.getReadAt())
                        .build()
        );
    }

    @Override
    public List<MessageStatusEntity> findAll() {
        return messageStatusRepository.findAll();
    }

    @Override
    public Optional<MessageStatusEntity> findById(Long messageStatusId) {
        return messageStatusRepository.findById(messageStatusId);
    }

    @Override
    public boolean existsById(Long messageStatusId) {
        return messageStatusRepository.existsById(messageStatusId);
    }

    @Override
    public long count() {
        return messageStatusRepository.count();
    }

    @Override
    public void deleteById(Long messageStatusId) {
        messageStatusRepository.deleteById(messageStatusId);
    }
}
