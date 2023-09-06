package com.justshop.apiserver.external.member.service;

import com.justshop.apiserver.api.user.member.controller.request.MemberCreateRequest;
import com.justshop.apiserver.api.user.member.service.MemberService;
import com.justshop.apiserver.api.user.member.service.request.MemberCreateServiceRequest;
import com.justshop.apiserver.api.user.member.service.response.MemberResponse;
import com.justshop.core.member.command.MemberManager;
import com.justshop.core.member.query.MemberReader;
import com.justshop.core.member.domain.Member;
import com.justshop.core.member.domain.enums.Gender;
import com.justshop.core.member.domain.enums.MemberStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberReader memberReader;

    @Autowired
    private MemberManager memberManager;

    @DisplayName("id로 회원 1명의 정보를 읽어온다.")
    @Test
    void getOneMember() {
        // given
        Member member = createMember("test123", "qwer1234$",
                "홍길동", "test123@naver.com",
                Gender.MAN,
                LocalDate.of(1997, 1, 3),
                MemberStatus.ACTIVE,
                true);

        Member savedMember = memberManager.create(member);

        // when
        MemberResponse result = memberService.getOneMember(savedMember.getId());

        // then
        Assertions.assertThat(result)
                .extracting("name", "email", "gender")
                .contains("홍길동", "test123@naver.com", Gender.MAN);
    }

    // TODO: 회원가입 로직 구체화 하기
    @DisplayName("회원의 요청 정보를 받아서 회원가입을 할 수 있다.")
    @Test
    void signUp() {
        // given
        MemberCreateServiceRequest request = MemberCreateServiceRequest.of(MemberCreateRequest.builder()
                .loginId("ggoma003")
                .password("qwer1234$")
                .name("홍길동")
                .email("test123@naver.com")
                .gender(Gender.MAN)
                .birthday(LocalDate.of(1997, 1, 3))
                .build());

        // when
        MemberResponse result = memberService.signUp(request);

        // then
        Assertions.assertThat(result)
                .extracting("name", "email", "gender")
                .contains("홍길동", "test123@naver.com", Gender.MAN);
    }

    // TODO: 회원가입 실패 테스트 작성
    // TODO: 이메일 인증 테스트 작성

    // 회원 엔티티 생성 메소드
    private Member createMember(String loginId,
                                String password,
                                String name,
                                String email,
                                Gender gender,
                                LocalDate birthday,
                                MemberStatus status,
                                boolean allowToMarketingNotification) {
        return Member.builder()
                .loginId(loginId)
                .password(password)
                .name(name)
                .email(email)
                .gender(gender)
                .birthday(birthday)
                .allowToMarketingNotification(allowToMarketingNotification)
                .build();
    }

}