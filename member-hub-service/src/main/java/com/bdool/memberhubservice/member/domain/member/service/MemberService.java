package com.bdool.memberhubservice.member.domain.member.service;

import com.bdool.memberhubservice.member.domain.member.entity.Member;
import com.bdool.memberhubservice.member.domain.member.entity.model.MemberModel;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    Member save(MemberModel memberModel);

    Optional<Member> findById(Long memberId);

    List<Member> findAll();

    long count();

    boolean existsById(Long memberId);

    boolean existsByEmail(String email);

    void deleteById(Long memberId);

    Optional<Member> findByEmail(String email);

    Long findIdByEmail(String receiverEmail);
}
