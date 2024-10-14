package com.bdool.memberhubservice.member.domain.member.repository;

import com.bdool.memberhubservice.member.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Long findIdByEmail(String receiverEmail);

    boolean existsByEmail(String email);
}
