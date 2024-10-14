package com.bdool.memberhubservice.mail.domain.log.service;

import com.bdool.memberhubservice.mail.domain.log.entity.Log;
import com.bdool.memberhubservice.mail.domain.log.entity.model.LogModel;

import java.util.List;
import java.util.Optional;

public interface LogService {

    Log save(LogModel logModel, boolean isSent);

    Optional<Log> findById(Long logId);

    List<Log> findAll();

    Long count();

    boolean existsById(Long logId);

    void deleteById(Long logId);
}
