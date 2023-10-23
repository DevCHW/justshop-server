package com.justshop.member.api.internal.application;

import com.justshop.core.error.ErrorCode;
import com.justshop.core.exception.BusinessException;
import com.justshop.member.DataFactoryUtil;
import com.justshop.member.IntegrationTestSupport;
import com.justshop.member.api.internal.application.dto.MemberResponse;
import com.justshop.member.domain.entity.Member;
import com.justshop.member.domain.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class InternalMemberServiceTest extends IntegrationTestSupport {

    @Autowired
    private InternalMemberService internalMemberService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원의 정보를 조회할 수 있다.")
    @Test
    void getMemberInfoSuccess() {
        // given
        Member member = DataFactoryUtil.generateMember();
        Member savedMember = memberRepository.save(member);

        Long memberId = savedMember.getId();

        // when
        MemberResponse myInfo = internalMemberService.getMemberInfo(memberId);

        // then
        assertThat(myInfo.getMemberId()).isEqualTo(memberId);
        assertThat(myInfo.getEmail()).isEqualTo(member.getEmail());
        assertThat(myInfo.getName()).isEqualTo(member.getName());
        assertThat(myInfo.getNickname()).isEqualTo(member.getNickname());
        assertThat(myInfo.getPoint()).isEqualTo(member.getPoint());
        assertThat(myInfo.getBirthday()).isEqualTo(member.getBirthday());
    }

    @DisplayName("조회하고자 하는 회원의 정보가 없다면 BusinessException이 발생한다.")
    @Test
    void getMemberInfo_fail() {
        // given
        Long memberId = 1L;

        // when & then
        Assertions.assertThatThrownBy(() -> internalMemberService.getMemberInfo(memberId))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.MEMBER_NOT_FOUND.getMessage());
    }

}