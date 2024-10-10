package com.bdool.memberhubservice.member.domain.member.controller;

import com.bdool.memberhubservice.member.domain.member.entity.Member;
import com.bdool.memberhubservice.member.domain.member.entity.model.MemberAuthResponse;
import com.bdool.memberhubservice.member.domain.member.entity.model.MemberModel;
import com.bdool.memberhubservice.member.domain.member.entity.model.MemberResponse;
import com.bdool.memberhubservice.member.domain.member.repository.MemberRepository;
import com.bdool.memberhubservice.member.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/")
    public ResponseEntity<Member> join(@RequestBody MemberModel memberModel) {
        Member savedMember = memberService.save(memberModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMember);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponse> getMemberById(@PathVariable Long memberId) {
        return ResponseEntity.ok(memberService.getMemberById(memberId));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<MemberAuthResponse> getMemberByEmail(@PathVariable String email) {
        return ResponseEntity.ok(memberService.getMemberByEmail(email));
    }

    @GetMapping("/me")
    public ResponseEntity<Member> getCurrentMember(@RequestHeader("Authorization") String accessToken) {
        return ResponseEntity.ok(memberService.getMemberByToken(accessToken));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMemberById(@PathVariable Long memberId) {
        memberService.deleteById(memberId);
        return ResponseEntity.noContent().build();
    }
}
