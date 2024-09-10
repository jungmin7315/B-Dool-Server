package com.bdool.memberhubservice.member.domain.member.repository;

import com.bdool.memberhubservice.member.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
