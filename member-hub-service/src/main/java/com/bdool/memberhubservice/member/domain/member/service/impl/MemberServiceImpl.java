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

import java.util.Map;
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
    public MemberResponse getMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + memberId));
        return new MemberResponse(member.getEmail());
    }

    @Override
    public Optional<Member> findMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Transactional
    @Override
    public void deleteById(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    @Transactional(readOnly = true)
    @Override
    public MemberAuthResponse getMemberByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));

        return new MemberAuthResponse(member.getEmail(), member.getRole().toString());
    }

    @Transactional(readOnly = true)
    @Override
    public Member getMemberByToken(String accessToken) {
        Map<String, Object> objectMap = jwtUtil.extractEmail(accessToken);
        String email = (String) objectMap.get("sub");
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
    }

    @Override
    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public Optional<Member> findMemberByEmail(String receiverEmail) {
        return memberRepository.findByEmail(receiverEmail);
    }
}
