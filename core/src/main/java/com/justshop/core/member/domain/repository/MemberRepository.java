package com.justshop.core.member.domain.repository;

import com.justshop.core.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
