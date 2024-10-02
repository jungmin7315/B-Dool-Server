package com.bdool.memberhubservice.member.domain.member.controller;

import com.bdool.memberhubservice.member.domain.member.entity.Member;
import com.bdool.memberhubservice.member.domain.member.entity.model.MemberModel;
import com.bdool.memberhubservice.member.domain.member.repository.MemberRepository;
import com.bdool.memberhubservice.member.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@CrossOrigin
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/")
    public ResponseEntity<Member> join(@RequestBody MemberModel memberModel) {
        return ResponseEntity.ok(memberService.save(memberModel));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<Optional<Member>> getMemberById(@PathVariable Long memberId) {
        return ResponseEntity.ok(memberService.findById(memberId));
    }


    @GetMapping("/me")
    public ResponseEntity<Optional<Member>> getCurrentMember(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(memberService.findByEmail(token));
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
        return ResponseEntity.noContent().build();
    }
}
