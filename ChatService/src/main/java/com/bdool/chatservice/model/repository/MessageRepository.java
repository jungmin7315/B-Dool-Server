package com.bdool.chatservice.model.repository;

import com.bdool.chatservice.model.entity.MessageEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends ReactiveMongoRepository<MessageEntity, UUID> {
    List<MessageEntity> findByMemberId(UUID memberId);
    Flux<MessageEntity> findMessageEntityBy(UUID id);
}

