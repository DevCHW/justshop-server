package com.justshop.member.domain.repository;

import com.justshop.jpa.JpaConfig;
import com.justshop.member.domain.entity.Member;
import com.justshop.member.domain.entity.enums.Gender;
import com.justshop.member.domain.entity.enums.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(JpaConfig.class)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("Member 저장 정상동작 테스트")
    @Test
    void memberRepository_save_success() {
        // given
        Member member = Member.builder()
                .name("hi")
                .nickname("nickname")
                .point(0)
                .build();
        // when
        Member result = memberRepository.save(member);

        // then
        assertThat(result.getName()).isEqualTo(member.getName());
        assertThat(result.getNickname()).isEqualTo(member.getNickname());
        assertThat(result.getPoint()).isEqualTo(member.getPoint());
    }

    @DisplayName("이미 존재하는 닉네임이라면 True를 반환한다.")
    @Test
    void exists_nickname_return_true() {
        // given
        Member member = generateMember();
        memberRepository.save(member);

        // when
        Boolean result = memberRepository.existsByNickname(member.getNickname());

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("존재하는 닉네임이 없다면 False를 반환한다.")
    @Test
    void exists_nickname_return_false() {
        // given
        String nickname = "NotExistsNickname";

        // when
        Boolean result = memberRepository.existsByNickname(nickname);

        // then
        assertThat(result).isFalse();
    }


    @DisplayName("이미 존재하는 이메일이라면 True를 반환한다.")
    @Test
    void exists_email_return_true() {
        // given
        Member member = generateMember();
        memberRepository.save(member);

        // when
        Boolean result = memberRepository.existsByEmail(member.getEmail());

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("존재하는 이메일이 없다면 False를 반환한다.")
    @Test
    void exists_email_return_false() {
        // given
        String email = "NotExistsNickname";

        // when
        Boolean result = memberRepository.existsByNickname(email);

        // then
        assertThat(result).isFalse();
    }

    // Member Factory method
    private Member generateMember() {
        String email = "testEmail@google.com";
        String password = "testPassword@";
        String name = "testName";
        String nickname = "testNickname";
        int point = 1000;
        LocalDate birthday = LocalDate.of(1997, 1, 3);
        Role memberRole = Role.USER;
        Gender gender = Gender.MAN;

        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .point(point)
                .birthday(birthday)
                .memberRole(memberRole)
                .gender(gender)
                .build();
    }
}