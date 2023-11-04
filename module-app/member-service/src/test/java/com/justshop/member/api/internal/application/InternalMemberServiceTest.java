package com.justshop.member.api.internal.application;

import com.justshop.client.dto.MemberResponse;
import com.justshop.core.error.ErrorCode;
import com.justshop.core.exception.BusinessException;
import com.justshop.member.DataFactoryUtil;
import com.justshop.member.IntegrationTestSupport;
import com.justshop.member.domain.entity.Member;
import com.justshop.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class InternalMemberServiceTest extends IntegrationTestSupport {

    @Autowired
    private InternalMemberService internalMemberService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원의 기본 정보와 기본배송지를 함께 조회할 수 있다.")
    @Test
    void getMemberInfoSuccess() {
        // given
        Member member = memberRepository.save(DataFactoryUtil.generateMemberWithDelivery());
        Long memberId = member.getId();

        // when
        MemberResponse result = internalMemberService.getMemberInfo(memberId);

        // then
        assertThat(result.getMemberId()).isEqualTo(memberId);
        assertThat(result.getEmail()).isEqualTo(member.getEmail());
        assertThat(result.getName()).isEqualTo(member.getName());
        assertThat(result.getNickname()).isEqualTo(member.getNickname());
        assertThat(result.getPoint()).isEqualTo(member.getPoint());
        assertThat(result.getBirthday()).isEqualTo(member.getBirthday());
        assertThat(result.getAddress().getCity()).isEqualTo(member.getBasicAddress().getAddress().getCity());
        assertThat(result.getAddress().getStreet()).isEqualTo(member.getBasicAddress().getAddress().getStreet());
        assertThat(result.getAddress().getZipcode()).isEqualTo(member.getBasicAddress().getAddress().getZipcode());
    }

    @DisplayName("조회하고자 하는 회원의 정보가 없다면 BusinessException이 발생한다.")
    @Test
    void getMemberInfo_fail() {
        // given
        Long memberId = 1L;

        // when & then
        assertThatThrownBy(() -> internalMemberService.getMemberInfo(memberId))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.MEMBER_NOT_FOUND.getMessage());
    }

}