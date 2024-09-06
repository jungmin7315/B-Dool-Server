package com.bdool.chatservice.model.repository;

import com.bdool.chatservice.model.entity.MemberEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MemberRepository extends MongoRepository<MemberEntity, UUID> {

}
