package com.bdool.chatservice.service.impl;

import com.bdool.chatservice.model.domain.MemberModel;
import com.bdool.chatservice.model.entity.MemberEntity;
import com.bdool.chatservice.model.repository.MemberRepository;
import com.bdool.chatservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberEntity save(MemberModel member) {
        return memberRepository.save(MemberEntity.builder()
                .memberId(member.getMemberId() == null ? UUID.randomUUID() : member.getMemberId())
                .name(member.getName())
                .favorited(member.isFavorited())
                .joinedAt(LocalDateTime.now())
                .channelId(member.getChannelId())
                .profileId(member.getProfileId())
                .build());
    }

    @Override
    public MemberEntity update(UUID memberId, MemberModel member) {
        return memberRepository.findById(memberId).map(existingMember -> {
            existingMember.setChannelId(member.getChannelId());
            existingMember.setFavorited(member.isFavorited());
            existingMember.setName(member.getName());
            existingMember.setProfileId(member.getProfileId());

            return memberRepository.save(existingMember);
        }).orElseThrow(() -> new RuntimeException("Channel not found with ID: " + memberId));
    }

    @Override
    public List<MemberEntity> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<MemberEntity> findById(UUID memberId) {
        return memberRepository.findById(memberId);
    }

    @Override
    public boolean existsById(UUID memberId) {
        return memberRepository.existsById(memberId);
    }

    @Override
    public long count() {
        return memberRepository.count();
    }

    @Override
    public void deleteById(UUID memberId) {
        memberRepository.deleteById(memberId);
    }
}
