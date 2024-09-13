package com.bdool.bdool.calendar.model.repository;

import com.bdool.bdool.calendar.model.entity.ParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<ParticipantEntity, Long> {
    List<ParticipantEntity> findByEventId(Long eventId);

    List<ParticipantEntity> findByProfileId(Long profileId);
    Long countByEventId(Long eventId);
}