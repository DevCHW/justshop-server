package com.justshop.member.domain.repository;

import com.justshop.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findIdByEmail(String email);

    Boolean existsByNickname(String nickname);

    Boolean existsByEmail(String email);
}
