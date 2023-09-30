package com.justshop.member.application;

import com.justshop.member.application.dto.SignUpServiceRequest;
import com.justshop.member.entity.Member;
import com.justshop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final MemberRepository memberRepository;

    public void signUp(SignUpServiceRequest request) {
        Member member = Member.builder().build();
        memberRepository.save(member);
    }
}
