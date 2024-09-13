package com.bdool.bdool.calendar.service.serviceImpl;

import com.bdool.bdool.calendar.model.entity.EventEntity;
import com.bdool.bdool.calendar.model.entity.ParticipantEntity;
import com.bdool.bdool.calendar.model.entity.ParticipantStatus;
import com.bdool.bdool.calendar.model.repository.EventRepository;
import com.bdool.bdool.calendar.model.repository.ParticipantRepository;
import com.bdool.bdool.calendar.service.ParticipantService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {
    private final ParticipantRepository participantRepository;
    private final EventRepository eventRepository;

    @Override
    public ParticipantEntity createParticipant(ParticipantEntity participantEntity) {
        ParticipantEntity participant = ParticipantEntity.builder()
                .event(participantEntity.getEvent())
                .profileId(participantEntity.getProfileId())
                .status(participantEntity.getStatus())
                .build();
        return participantRepository.save(participant);
    }

    @Override
    public ParticipantEntity updateParticipant(Long id, ParticipantStatus status) {

        ParticipantEntity existingParticipant = participantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 참가자가 없습니다."));

        existingParticipant.setStatus(status);

        return participantRepository.save(existingParticipant);
    }

    @Override
    public Optional<ParticipantEntity> getParticipantById(Long id) {
        return participantRepository.findById(id);
    }

    @Override
    public void deleteParticipant(Long id) {
        ParticipantEntity participant = participantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 참가자가 없습니다."));

        /*EventEntity event = participant.getEvent();
        if (!event.getProfileId().equals(currentUserId)) {
            throw new SecurityException("삭제할 권한이 없다");
        }*/

        participantRepository.delete(participant);
    }

    @Override
    public List<ParticipantEntity> getParticipantsByEventId(Long eventId) {
        return participantRepository.findByEventId(eventId);
    }

    @Override
    public long countParticipantsByEventId(Long eventId) {
        return participantRepository.countByEventId(eventId);
    }

    @Override
    public List<EventEntity> getEventsForParticipant(Long profileId) {
        List<ParticipantEntity> participants = participantRepository.findByProfileId(profileId);
        return participants.stream()
                .map(ParticipantEntity::getEvent)
                .toList();
    }

    @Override
    public List<ParticipantEntity> getParticipants(){
        return participantRepository.findAll();
    }
}
