package com.justshop.core.member.command;

import com.justshop.core.member.domain.Member;
import com.justshop.core.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.justshop.core.member.domain.enums.Gender.*;
import static com.justshop.core.member.domain.enums.MemberStatus.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberManagerTest {

    @Autowired
    private MemberManager memberManager;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원을 생성한다.")
    @Test
    void signUp() {
        // given
        Member target = generateMember();

        // when
        Member result = memberManager.create(target);

        // then
        assertThat(result.getLoginId()).isEqualTo(target.getLoginId());
        assertThat(result.getPassword()).isEqualTo(target.getPassword());
        assertThat(result.getName()).isEqualTo(target.getName());
        assertThat(result.getEmail()).isEqualTo(target.getEmail());
        assertThat(result.getNickname()).isEqualTo(target.getNickname());
        assertThat(result.getGender()).isEqualTo(target.getGender());
        assertThat(result.getPhone()).isEqualTo(target.getPhone());
        assertThat(result.getBirthday()).isEqualTo(target.getBirthday());
        assertThat(result.getStatus()).isEqualTo(target.getStatus());
        assertThat(result.getPoint()).isEqualTo(target.getPoint());
    }

    private Member generateMember() {
        return Member.builder()
                .loginId("test123")
                .password("qwer1234$")
                .name("홍길동")
                .email("example@google.com")
                .nickname("HelloWorld")
                .gender(MAN)
                .phone("01012345678")
                .birthday(LocalDate.of(1997, 1, 3))
                .status(ACTIVE)
                .point(0)
                .allowToMarketingNotification(true)
                .build();
    }

}