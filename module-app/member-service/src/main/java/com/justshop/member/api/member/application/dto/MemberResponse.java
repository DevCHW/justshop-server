package com.justshop.member.api.member.application.dto;

import com.justshop.member.entity.Member;
import com.justshop.member.entity.enums.Gender;
import com.justshop.member.entity.enums.MemberStatus;
import com.justshop.member.entity.enums.Role;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberResponse {

    private Long memberId;
    private String email; // 이메일
    private String name; //이름
    private String nickname; // 닉네임
    private int point; // 포인트
    private LocalDate birthday; // 생년월일
    private Role memberRole; // 권한
    private Gender gender; // 성별
    private MemberStatus status; // 상태

    @Builder
    private MemberResponse(Long memberId, String email, String name, String nickname, int point, LocalDate birthday, Role memberRole, Gender gender, MemberStatus status) {
        this.memberId = memberId;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.point = point;
        this.birthday = birthday;
        this.memberRole = memberRole;
        this.gender = gender;
        this.status = status;
    }

    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .point(member.getPoint())
                .birthday(member.getBirthday())
                .memberRole(member.getMemberRole())
                .gender(member.getGender())
                .status(member.getStatus())
                .build();
    }

}
