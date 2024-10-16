package com.bdool.bdool.calendar.model.repository;

import com.bdool.bdool.calendar.model.entity.AttendeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendeeRepository extends JpaRepository<AttendeeEntity, Long> {
    List<AttendeeEntity> findByEventId(Long eventId);

    List<AttendeeEntity> findByProfileId(Long profileId);

    Optional<AttendeeEntity> findByEventIdAndProfileId(Long eventId, Long profileId);
    Long countByEventId(Long eventId);


}