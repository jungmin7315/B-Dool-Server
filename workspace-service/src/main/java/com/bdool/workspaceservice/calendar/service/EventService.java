package com.bdool.bdool.calendar.service;

import com.bdool.bdool.calendar.model.domain.EventRequest;
import com.bdool.bdool.calendar.model.entity.EventEntity;

import java.util.Optional;

public interface EventService {
    EventEntity createEvent(EventRequest request);

    EventEntity updateEvent(Long eventId, EventRequest request);

    void deleteEvent(Long eventId);

    Optional<EventEntity> getEventById(Long eventId);

    boolean existsEvent(Long eventId);

    long countEvent();
















}
