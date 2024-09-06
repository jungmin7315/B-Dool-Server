package com.bdool.chatservice.service;

import com.bdool.chatservice.model.domain.FileModel;
import com.bdool.chatservice.model.domain.MemberModel;
import com.bdool.chatservice.model.entity.FileEntity;
import com.bdool.chatservice.model.entity.MemberEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface MemberService {
    MemberEntity save(MemberModel member);

    MemberEntity update(UUID memberId, MemberModel member);

    List<MemberEntity> findAll();

    Optional<MemberEntity> findById(UUID memberId);

    boolean existsById(UUID memberId);

    long count();

    void deleteById(UUID memberId);
}
