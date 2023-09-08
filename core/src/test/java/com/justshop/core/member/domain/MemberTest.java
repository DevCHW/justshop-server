package com.justshop.core.member.domain;

import com.justshop.core.member.domain.enums.Gender;
import com.justshop.core.member.domain.enums.MemberStatus;
import com.justshop.core.member.exception.NotEnoughPointException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class MemberTest {

    @DisplayName("회원 비밀번호를 암호화하면 암호화된 값으로 바뀌어야 한다.")
    @Test
    void encryptPassword() {
        // given
        String inputPassword = "qwer1324$";
        Member member = generateMember(inputPassword);

        // when
        member.encryptPassword();
        String result = member.getPassword();

        // then
        assertThat(result).isNotEqualTo(inputPassword);
    }

    @DisplayName("포인트를 증가시킨다.")
    @Test
    void addPoint() {
        // given
        Member member = generateMember();

        int startPoint = member.getPoint();
        int amount = 5000;

        int target = startPoint + amount;

        // when
        member.addPoint(amount);
        int result = member.getPoint();

        // then
        assertThat(result).isEqualTo(target);
    }

    @DisplayName("포인트를 차감한다.")
    @Test
    void usePoint() {
        // given
        Member member = generateMember();

        int startPoint = member.getPoint();
        int amount = 2500;

        int target = startPoint - amount;

        // when
        member.usePoint(amount);
        int result = member.getPoint();

        // then
        assertThat(result).isEqualTo(target);
    }

    @DisplayName("포인트를 차감할 때 보유포인트가 부족하다면 NotEnoughPointException이 발생한다.")
    @Test
    void usePointNotEnoughException() {
        // given
        Member member = generateMember();

        int startPoint = member.getPoint();
        int amount = startPoint + 1;

        // when
        assertThatThrownBy(() -> member.usePoint(amount))
                .isInstanceOf(NotEnoughPointException.class)
                .hasMessage("보유 포인트가 부족합니다.");
    }

    // Constructor
    private Member generateMember() {
        return Member.builder()
                .loginId("loginId")
                .password("qwer1234$")
                .name("홍길동")
                .email("test123@google.com")
                .gender(Gender.MAN)
                .birthday(LocalDate.of(1997, 1, 3))
                .status(MemberStatus.ACTIVE)
                .point(5000)
                .allowToMarketingNotification(true)
                .build();
    }

    private Member generateMember(String password) {
        return Member.builder()
                .loginId("loginId")
                .password(password)
                .name("홍길동")
                .email("test123@google.com")
                .gender(Gender.MAN)
                .birthday(LocalDate.of(1997, 1, 3))
                .status(MemberStatus.ACTIVE)
                .point(5000)
                .allowToMarketingNotification(true)
                .build();
    }
}