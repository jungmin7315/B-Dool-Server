package com.bdool.chatservice.controller;

import com.bdool.chatservice.model.domain.MemberModel;
import com.bdool.chatservice.model.entity.MemberEntity;
import com.bdool.chatservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody MemberModel member) {
        return ResponseEntity.ok(memberService.save(member));
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<?> update(@PathVariable UUID memberId, @RequestBody MemberModel member) {
        return ResponseEntity.ok(memberService.update(memberId, member));
    }

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        List<MemberEntity> members = memberService.findAll();
        if(members.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(members);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<?> findById(@PathVariable UUID memberId) {
        return ResponseEntity.ok(memberService.findById(memberId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build()));
    }

    @GetMapping("/exists/{sessionId}")
    public ResponseEntity<?> existsById(@PathVariable UUID memberId) {
        return ResponseEntity.ok(memberService.existsById(memberId));
    }

    @GetMapping("/count")
    public ResponseEntity<?> count() {
        return ResponseEntity.ok(memberService.count());
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<?> deleteById(@PathVariable UUID memberId) {
        memberService.deleteById(memberId);
        return ResponseEntity.noContent().build();
    }
}
