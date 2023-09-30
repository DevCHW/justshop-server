package com.justshop.member.repository;

import com.justshop.jpa.config.JpaConfig;
import com.justshop.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

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
    
}