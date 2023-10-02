package com.justshop.member.api.member.application;

import com.justshop.exception.BusinessException;
import com.justshop.member.api.member.application.dto.MemberResponse;
import com.justshop.member.entity.Member;
import com.justshop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.justshop.error.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
}
