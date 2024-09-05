package com.bdool.messageservice.model.repository;

import com.bdool.messageservice.model.entity.MessageStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageStatusRepository extends JpaRepository<MessageStatusEntity, Long> {

}
