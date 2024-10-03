package com.bdool.memberhubservice.member.domain.member.service.impl;

import com.bdool.memberhubservice.member.domain.global.util.JwtUtil;
import com.bdool.memberhubservice.member.domain.member.entity.Member;
import com.bdool.memberhubservice.member.domain.member.entity.Role;
import com.bdool.memberhubservice.member.domain.member.entity.model.MemberAuthResponse;
import com.bdool.memberhubservice.member.domain.member.entity.model.MemberModel;
import com.bdool.memberhubservice.member.domain.member.entity.model.MemberResponse;
import com.bdool.memberhubservice.member.domain.member.repository.MemberRepository;
import com.bdool.memberhubservice.member.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    @Override
    public Member save(MemberModel memberModel) {
        Member member = Member.builder()
                .email(memberModel.getEmail())
                .role(Role.ROLE_USER)
                .build();
        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    @Override
    public MemberResponse findById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + memberId));
        return new MemberResponse(member.getEmail());
    }

    @Transactional
    @Override
    public void deleteById(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    @Transactional(readOnly = true)
    @Override
    public MemberAuthResponse findByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));

        return new MemberAuthResponse(member.getEmail(), member.getRole().toString());
    }

    @Transactional(readOnly = true)
    @Override
    public MemberResponse getMemberByToken(String accessToken) {
        String email = jwtUtil.extractEmail(accessToken);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
        return new MemberResponse(member.getEmail());
    }
}
