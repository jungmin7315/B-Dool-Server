package com.bdool.workspaceservice.calendar.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bdool.workspaceservice.calendar.model.entity.EventEntity;
@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
}