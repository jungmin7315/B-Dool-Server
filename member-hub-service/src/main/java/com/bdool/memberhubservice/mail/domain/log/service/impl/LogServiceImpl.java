package com.bdool.memberhubservice.mail.domain.log.service.impl;

import com.bdool.memberhubservice.mail.domain.log.entity.Log;
import com.bdool.memberhubservice.mail.domain.log.entity.model.LogModel;
import com.bdool.memberhubservice.mail.domain.log.repository.LogRepository;
import com.bdool.memberhubservice.mail.domain.log.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;

    @Override
    public Log save(LogModel logModel, boolean isSent) {
        Log log = Log.builder()
                .email(logModel.getEmail())
                .subject(logModel.getSubject())
                .body(logModel.getBody())
                .isSent(isSent)
                .build();
        return logRepository.save(log);
    }

    @Override
    public Optional<Log> findById(Long logId) {
        return logRepository.findById(logId);
    }

    @Override
    public List<Log> findAll() {
        return logRepository.findAll();
    }

    @Override
    public Long count() {
        return logRepository.count();
    }

    @Override
    public boolean existsById(Long logId) {
        return logRepository.existsById(logId);
    }

    @Override
    public void deleteById(Long logId) {
        logRepository.deleteById(logId);
    }
}
