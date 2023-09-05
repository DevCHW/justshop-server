package com.justshop.core.member.command;

import com.justshop.core.member.command.MemberManager;
import com.justshop.core.member.domain.Member;
import com.justshop.core.member.domain.repository.MemberRepository;
import com.justshop.core.member.domain.enumerated.Gender;
import com.justshop.core.member.domain.enumerated.MemberStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
@Transactional
class MemberManagerTest {

    @Autowired
    private MemberManager memberManager;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원 등록을 할 수 있다.")
    @Test
    void signUp() {
        // given
        Member member = createMember("ggoma003", "qwer1234$",
                "최현우", "ggoma003@naver.com",
                Gender.MAN,
                LocalDate.of(1997, 1, 3),
                MemberStatus.ACTIVE,
                true);

        // when
        Member result = memberManager.create(member);

        // then
        Assertions.assertThat(result)
                .extracting("loginId", "password", "name", "email", "gender", "birthday", "status", "allowToMarketingNotification")
                .contains("ggoma003",
                        "qwer1234$",
                        "최현우",
                        "ggoma003@naver.com",
                        Gender.MAN,
                        LocalDate.of(1997, 1, 3),
                        MemberStatus.ACTIVE,
                        true);
    }

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