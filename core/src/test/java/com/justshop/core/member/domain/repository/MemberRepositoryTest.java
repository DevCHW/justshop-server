package com.justshop.core.member.domain.repository;

import com.justshop.core.member.domain.Member;
import com.justshop.core.member.domain.enums.Gender;
import com.justshop.core.member.domain.enums.MemberStatus;
import com.justshop.core.member.persistence.jpa.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원의 이메일이 존재하면 true를 반환한다.")
    @Test
    void existsByEmailReturnTrue() {
        // given
        String email = "test123@google.com";
        Member member = generateMemberWithEmail(email);
        memberRepository.save(member);

        // when
        boolean result = memberRepository.existsByEmail(email);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("회원의 이메일이 존재하지 않는다면 false를 반환한다.")
    @Test
    void existsByEmailReturnFalse() {
        // given
        String email = "noExistsEmail@google.com";

        // when
        boolean result = memberRepository.existsByEmail(email);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("회원의 닉네임이 존재한다면 true를 반환한다.")
    @Test
    void existsByNicknameReturnTrue() {
        // given
        String nickname = "existsNickname";
        Member member = generateMemberWithNickname(nickname);
        memberRepository.save(member);

        boolean result = memberRepository.existsByNickname(nickname);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("회원의 닉네임이 존재하지 않는다면 false를 반환한다.")
    @Test
    void existsByNicknameReturnFalse() {
        // given
        String nickname = "noExists";

        // when
        boolean result = memberRepository.existsByNickname(nickname);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("회원의 로그인 ID가 존재한다면 true를 반환한다.")
    @Test
    void existsByLoginIdReturnTrue() {
        // given
        String loginId = "existsNickname";
        Member member = generateMemberWithLoginId(loginId);
        memberRepository.save(member);

        boolean result = memberRepository.existsByLoginId(loginId);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("회원의 로그인 ID가 존재하지 않는다면 false를 반환한다.")
    @Test
    void existsByLoginIdReturnFalse() {
        // given
        String loginId = "noExists";

        // when
        boolean result = memberRepository.existsByLoginId(loginId);

        // then
        assertThat(result).isFalse();
    }

    // Member Constructor
    private Member generateMemberWithLoginId(String loginId) {
        return Member.builder()
                .loginId(loginId)
                .password("qwer1234$")
                .name("홍길동")
                .nickname("testNickname")
                .email("test123@google.com")
                .gender(Gender.MAN)
                .birthday(LocalDate.of(1997, 1, 3))
                .status(MemberStatus.ACTIVE)
                .point(5000)
                .allowToMarketingNotification(true)
                .build();
    }

    private Member generateMemberWithEmail(String email) {
        return Member.builder()
                .loginId("loginId")
                .password("qwer1234$")
                .name("홍길동")
                .nickname("testNickname")
                .email(email)
                .gender(Gender.MAN)
                .birthday(LocalDate.of(1997, 1, 3))
                .status(MemberStatus.ACTIVE)
                .point(5000)
                .allowToMarketingNotification(true)
                .build();
    }

    private Member generateMemberWithNickname(String nickname) {
        return Member.builder()
                .loginId("loginId")
                .password("qwer1234$")
                .name("홍길동")
                .nickname(nickname)
                .email("test123@google.com")
                .gender(Gender.MAN)
                .birthday(LocalDate.of(1997, 1, 3))
                .status(MemberStatus.ACTIVE)
                .point(5000)
                .allowToMarketingNotification(true)
                .build();
    }

}