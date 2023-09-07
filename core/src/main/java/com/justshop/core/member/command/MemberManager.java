package com.justshop.core.member.command;

import com.justshop.core.member.domain.repository.MemberRepository;
import com.justshop.core.member.domain.Member;
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
        member.encryptPassword(); // 비밀번호 암호화
        return memberRepository.save(member);
    }

}
