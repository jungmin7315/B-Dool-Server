package com.bdool.memberhubservice.member.domain.member.service;

import com.bdool.memberhubservice.member.domain.member.entity.Member;
import com.bdool.memberhubservice.member.domain.member.entity.model.MemberAuthResponse;
import com.bdool.memberhubservice.member.domain.member.entity.model.MemberModel;
import com.bdool.memberhubservice.member.domain.member.entity.model.MemberResponse;

import java.util.Optional;

public interface MemberService {

    Member save(MemberModel memberModel);

    MemberResponse findById(Long memberId);

    Optional<Member> findMemberById(Long memberId);

    void deleteById(Long memberId);

    MemberAuthResponse findByEmail(String email);

    MemberResponse getMemberByToken(String accessToken);
}
