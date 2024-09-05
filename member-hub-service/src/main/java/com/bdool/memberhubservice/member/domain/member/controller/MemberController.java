package com.bdool.memberhubservice.member.domain.member.controller;

import com.bdool.memberhubservice.member.domain.member.entity.Member;
import com.bdool.memberhubservice.member.domain.member.entity.model.MemberModel;
import com.bdool.memberhubservice.member.domain.member.repository.MemberRepository;
import com.bdool.memberhubservice.member.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/")
    public ResponseEntity<Member> join(MemberModel memberModel) {
        return ResponseEntity.ok(memberService.save(memberModel));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long memberId) {
        return memberService.findById(memberId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/")
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberRepository.findAll());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getMemberCount() {
        return ResponseEntity.ok(memberService.count());
    }

    @GetMapping("/exists/{memberId}")
    public ResponseEntity<Boolean> checkMemberExists(@PathVariable Long memberId) {
        return ResponseEntity.ok(memberService.existsById(memberId));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMemberById(@PathVariable Long memberId) {
        memberService.deleteById(memberId);
        return ResponseEntity.ok().build();
    }
}
