package com.justshop.member.repository;

import com.justshop.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member save(Member member);
}
