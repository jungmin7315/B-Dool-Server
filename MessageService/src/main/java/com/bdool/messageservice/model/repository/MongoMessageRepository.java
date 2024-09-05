package com.bdool.messageservice.model.repository;

import com.bdool.messageservice.model.entity.MongoMessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface MongoMessageRepository extends MongoRepository<MongoMessageEntity, UUID> {

}

