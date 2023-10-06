package com.justshop.member.domain.entity;

import com.justshop.core.exception.BusinessException;
import com.justshop.member.domain.entity.enums.Gender;
import com.justshop.member.domain.entity.enums.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class MemberTest {

    @DisplayName("변경시킬 비밀번호를 받아서 회원의 비밀번호를 변경시킨다.")
    @Test
    void changePassword() {
        // given
        Member member = generateMember();
        String changePassword = "changePassword";

        // when
        member.changePassword(changePassword);

        // then
        assertThat(member.getPassword()).isEqualTo(changePassword);
    }

    @DisplayName("변경시킬 닉네임을 받아서 회원의 닉네임을 변경시킨다.")
    @Test
    void changeNickname() {
        // given
        Member member = generateMember();
        String changeNickname = "changeNickname";

        // when
        member.changeNickname(changeNickname);
    
        // then
        assertThat(member.getNickname()).isEqualTo(changeNickname);
    }
    
    @DisplayName("적립시킬 포인트의 양을 받아서 회원의 포인트를 증가시킨다.")
    @Test
    void addPoint() {
        // given
        Member member = generateMember();
        Long amount = 1000L;
        Integer expect = member.getPoint() + amount.intValue();

        // when
        member.addPoint(amount);

        // then
        assertThat(member.getPoint()).isEqualTo(expect);
    }

    @DisplayName("차감시킬 포인트의 양을 받아서 회원의 포인트를 차감시킨다.")
    @Test
    void decreasePoint() {
        // given
        Member member = generateMember();
        Long amount = 1000L;
        Integer expect = member.getPoint() - amount.intValue();

        // when
        member.decreasePoint(amount);

        // then
        assertThat(member.getPoint()).isEqualTo(expect);
    }

    @DisplayName("차감시킬 포인트보다 보유 포인트가 적을 시 BusinessException 예외가 발생한다.")
    @Test
    void decreasePoint_fail() {
        // given
        Member member = generateMember();
        Long amount = member.getPoint() + 1L;

        // when & then
        assertThatThrownBy(() -> member.decreasePoint(amount))
                .isInstanceOf(BusinessException.class)
                .hasMessage("회원의 보유 포인트가 부족하여 포인트를 차감할 수 없습니다.");
    }

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