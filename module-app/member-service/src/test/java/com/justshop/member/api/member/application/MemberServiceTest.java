package com.justshop.member.api.member.application;

import com.justshop.exception.BusinessException;
import com.justshop.member.DataFactoryUtil;
import com.justshop.member.api.member.application.dto.MemberResponse;
import com.justshop.member.entity.Member;
import com.justshop.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static com.justshop.error.ErrorCode.MEMBER_NOT_FOUND;
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

    @DisplayName("나의 정보를 조회할 수 있다.")
    @Test
    void getMemberInfoSuccess() {
        // given
        Member member = DataFactoryUtil.generateMember();
        Member savedMember = memberRepository.save(member);

        Long myId = savedMember.getId();

        // when
        MemberResponse myInfo = memberService.getMemberInfo(myId);

        // then
        assertThat(myInfo.getMemberId()).isEqualTo(myId);
        assertThat(myInfo.getEmail()).isEqualTo(member.getEmail());
        assertThat(myInfo.getName()).isEqualTo(member.getName());
        assertThat(myInfo.getNickname()).isEqualTo(member.getNickname());
        assertThat(myInfo.getPoint()).isEqualTo(member.getPoint());
        assertThat(myInfo.getBirthday()).isEqualTo(member.getBirthday());

    }

    @DisplayName("변경할 비밀번호와 회원의 ID를 받아 회원의 비밀번호를 변경할 수 있다.")
    @Test
    void editPasswordSuccess() {
        // given
        Member savedMember = memberRepository.save(DataFactoryUtil.generateMember());

        Long myId = savedMember.getId();
        String changePassword = "changePassword";

        // when
        memberService.editPassword(myId, changePassword); // 비밀번호 변경

        Member findMember = memberRepository.findById(myId).get(); // 다시 조회
        User userDetails = new User(findMember.getEmail(), findMember.getPassword(), true, true, true, true,
                new ArrayList<>());

        // then
        assertThat(passwordEncoder.matches(changePassword, userDetails.getPassword())).isTrue(); //변경하려는 비밀번호로 잘 변경되어있어야 한다.
    }
    
    @DisplayName("회원ID를 받아서 회원탈퇴를 할 수 있다.")
    @Test
    void deleteMemberSuccess() {
        // given
        Member member = DataFactoryUtil.generateMember();
        Member savedMember = memberRepository.save(member);

        // when
        memberService.deleteMember(savedMember.getId());

        // then
        assertThatThrownBy(() -> memberRepository.findById(member.getId())
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND)))
                .isInstanceOf(BusinessException.class)
                .hasMessage(MEMBER_NOT_FOUND.getDescription());
    }

    @DisplayName("변경할 닉네임과 회원의 ID를 받아 회원의 닉네임을 변경할 수 있다.")
    @Test
    void editNicknameSuccess() {
        // given
        Member savedMember = memberRepository.save(DataFactoryUtil.generateMember());

        Long myId = savedMember.getId();
        String changeNickname = "changeNickname";

        // when
        memberService.editNickname(myId, changeNickname); // 닉네임 변경

        Member findMember = memberRepository.findById(myId).get(); // 다시 조회

        // then
        assertThat(findMember.getNickname()).isEqualTo(changeNickname); //변경하려는 닉네임으로 잘 변경되어있어야 한다.
    }

}