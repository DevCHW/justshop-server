package com.justshop.member.api.external.application;

import com.justshop.core.exception.BusinessException;
import com.justshop.member.domain.entity.Member;
import com.justshop.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.justshop.core.error.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 비밀번호 변경
    @Transactional
    public void editPassword(Long memberId, String password) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));

        findMember.changePassword(passwordEncoder.encode(password));
    }

    // 회원 탈퇴
    @Transactional
    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    // 닉네임 변경
    @Transactional
    public void editNickname(Long memberId, String nickname) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));

        findMember.changeNickname(nickname);
    }

    // 닉네임 중복체크
    public Boolean existsNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    // 이메일 중복체크
    public Boolean existsEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    // 포인트 변경
    @Transactional
    public void updatePoint(Long memberId, Long amount, String type) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));

        if ("ADD".equals(type)) {
            member.addPoint(amount);
        }

        if ("DEDUCTION".equals(type)) {
            member.decreasePoint(amount);
        }

    }
}
