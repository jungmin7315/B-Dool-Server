package com.bdool.messageservice.model.repository;

import com.bdool.messageservice.model.entity.MessageFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageFileRepository extends JpaRepository<MessageFileEntity, Long> {

}
