package com.justshop.member.api.member.application;

import com.justshop.error.ErrorCode;
import com.justshop.exception.BusinessException;
import com.justshop.member.api.member.application.dto.MemberResponse;
import com.justshop.member.entity.Member;
import com.justshop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.justshop.error.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 내정보 조회
    public MemberResponse getMemberInfo(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));

        return MemberResponse.from(findMember);
    }

    // 비밀번호 변경
    public void passwordEdit(Long memberId, String password) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));

        findMember.changePassword(passwordEncoder.encode(password));
    }
}
