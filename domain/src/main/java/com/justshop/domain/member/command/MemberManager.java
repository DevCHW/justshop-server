package com.justshop.domain.member.command;

import com.justshop.domain.member.domain.Member;
import com.justshop.domain.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberManager {

    private final MemberRepository memberRepository;

    public Member create(Member member) {
        return memberRepository.save(member);
    }

}
