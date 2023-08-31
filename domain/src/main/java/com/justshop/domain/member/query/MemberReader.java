package com.justshop.domain.member.query;

import com.justshop.domain.member.domain.Member;
import com.justshop.domain.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberReader {

    private final MemberRepository memberRepository;

    public Member read(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new RuntimeException("찾을 수 없는 회원입니다."));
    }

}
