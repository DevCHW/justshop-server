package com.justshop.member.api.internal.application;

import com.justshop.core.exception.BusinessException;
import com.justshop.member.api.internal.application.dto.MemberResponse;
import com.justshop.member.domain.entity.Member;
import com.justshop.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.justshop.core.error.ErrorCode.MEMBER_NOT_FOUND;
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InternalMemberService {

    private final MemberRepository memberRepository;

    /* 회원 정보 조회 */
    public MemberResponse getMemberInfo(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));

        return MemberResponse.from(findMember);
    }

}
