package com.bdool.memberhubservice.member.domain.member.service;

import com.bdool.memberhubservice.member.domain.member.entity.Member;
import com.bdool.memberhubservice.member.domain.member.entity.model.MemberAuthResponse;
import com.bdool.memberhubservice.member.domain.member.entity.model.MemberModel;
import com.bdool.memberhubservice.member.domain.member.entity.model.MemberResponse;

import java.util.Optional;

public interface MemberService {

    Member save(MemberModel memberModel);

    MemberResponse getMemberById(Long memberId);

    Optional<Member> findMemberById(Long memberId);

    void deleteById(Long memberId);

    MemberAuthResponse getMemberByEmail(String email);

    MemberResponse getMemberByToken(String accessToken);

    boolean existsByEmail(String email);

    Optional<Member> findMemberByEmail(String receiverEmail);
}
