package com.justshop.member.api.member.application;

import com.justshop.member.api.member.application.dto.MemberResponse;
import com.justshop.member.entity.Member;
import com.justshop.member.entity.enums.Gender;
import com.justshop.member.entity.enums.Role;
import com.justshop.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @DisplayName("나의 정보를 조회한다.")
    @Test
    void getMemberInfoSuccess() {
        // given
        String email = "testEmail@google.com";
        String password = "testPassword@";
        String name = "testName";
        String nickname = "testNickname";
        int point = 1000;
        LocalDate birthday = LocalDate.of(1997, 1, 3);
        Role memberRole = Role.USER;
        Gender gender = Gender.MAN;

        Member savedMember = memberRepository.save(generateMember(email, password, name, nickname, point, birthday, memberRole, gender));

        Long myId = savedMember.getId();

        // when
        MemberResponse myInfo = memberService.getMemberInfo(myId);

        // then
        assertThat(myInfo.getMemberId()).isEqualTo(myId);
        assertThat(myInfo.getEmail()).isEqualTo(email);
        assertThat(myInfo.getName()).isEqualTo(name);
        assertThat(myInfo.getNickname()).isEqualTo(nickname);
        assertThat(myInfo.getPoint()).isEqualTo(point);
        assertThat(myInfo.getBirthday()).isEqualTo(birthday);

    }

    @DisplayName("변경할 비밀번호와 회원의 ID를 받아 비밀번호를 변경할 수 있다.")
    @Test
    void passwordEditSuccess() {
        // given
        String email = "testEmail@google.com";
        String password = "testPassword@";
        String name = "testName";
        String nickname = "testNickname";
        int point = 1000;
        LocalDate birthday = LocalDate.of(1997, 1, 3);
        Role memberRole = Role.USER;
        Gender gender = Gender.MAN;

        Member savedMember = memberRepository.save(generateMember(email, password, name, nickname, point, birthday, memberRole, gender));

        Long myId = savedMember.getId();
        String changePassword = "changePassword";

        // when
        memberService.passwordEdit(myId, changePassword); // 비밀번호 변경

        Member findMember = memberRepository.findById(myId).get(); // 다시 조회
        User userDetails = new User(findMember.getEmail(), findMember.getPassword(), true, true, true, true,
                new ArrayList<>());

        // then
        assertThat(passwordEncoder.matches(changePassword, userDetails.getPassword())).isTrue(); //변경하려는 비밀번호로 잘 변경되어있어야 한다.
    }

    private Member generateMember(String email, String password, String name, String nickname, int point, LocalDate birthday, Role memberRole, Gender gender) {
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