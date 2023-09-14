package com.justshop.core.member.command;

import com.justshop.core.member.exception.NotEnoughPointException;
import com.justshop.core.member.domain.Member;
import com.justshop.core.member.domain.PointEventHistory;
import com.justshop.core.member.domain.enums.Gender;
import com.justshop.core.member.domain.enums.MemberStatus;
import com.justshop.core.member.implementation.command.PointManager;
import com.justshop.core.member.persistence.jpa.MemberRepository;
import com.justshop.core.member.persistence.jpa.PointEventHistoryRepository;
import com.justshop.core.support.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class PointManagerTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PointManager pointManager;

    @Autowired
    private PointEventHistoryRepository pointEventHistoryRepository;

    @DisplayName("회원의 포인트가 적립되면 포인트를 적립하고 적립내역을 기록한다.")
    @Test
    void append() {
        // given
        Member member = memberRepository.save(createMember(0));
        int amount = 5000;
        String message = "포인트 적립 메세지";

        // when
        PointEventHistory result = pointManager.append(member, amount, message);

        // then
        assertThat(member.getPoint()).isEqualTo(5000);
        assertThat(result.getAmount()).isEqualTo(amount);
        assertThat(result.getEventMessage()).isEqualTo(message);
    }

    @DisplayName("회원이 포인트를 사용하면 포인트를 차감하고 내역을 기록한다.")
    @Test
    void use() {
        // given
        Member member = memberRepository.save(createMember(5000));
        int amount = 2500;
        String message = "포인트 차감 메세지";

        // when
        PointEventHistory result = pointManager.use(member, amount, message);

        // then
        assertThat(member.getPoint()).isEqualTo(2500);
        assertThat(result.getAmount()).isEqualTo(amount);
        assertThat(result.getEventMessage()).isEqualTo(message);

    }

    @DisplayName("포인트를 사용할 때 포인트가 부족하다면 NotEnoughPointException 예외가 발생한다.")
    @Test
    void useExceptionCase() {
        // given
        int memberPoint = 3000;
        Member member = memberRepository.save(createMember(memberPoint));
        int useAmount = 4000;
        String message = "포인트 차감 메세지";

        // then
        assertThatThrownBy(() -> pointManager.use(member, useAmount, message))
                .isInstanceOf(NotEnoughPointException.class)
                .hasMessage("포인트가 부족합니다.");
    }

    private Member createMember(int point) {
        return Member.builder()
                .loginId("test123")
                .password("qwer1234$")
                .name("홍길동")
                .email("test123@naver.com")
                .gender(Gender.MAN)
                .status(MemberStatus.ACTIVE)
                .birthday(LocalDate.of(1997, 1, 3))
                .point(point)
                .allowToMarketingNotification(true)
                .build();
    }
}