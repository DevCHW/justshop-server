package com.justshop.member.application.dto;

import com.justshop.member.entity.Member;
import com.justshop.member.entity.enums.Gender;
import com.justshop.member.entity.enums.MemberStatus;
import com.justshop.member.entity.enums.Role;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SignUpServiceRequest {

    private String loginId;
    private String password;
    private String name;
    private String nickname;
    private int point;
    private LocalDate birthday;
    private Role memberRole;
    private Gender gender;
    private MemberStatus status;

    @Builder
    public SignUpServiceRequest(String loginId, String password, String name, String nickname, int point, LocalDate birthday, Role memberRole, Gender gender, MemberStatus status) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.point = point;
        this.birthday = birthday;
        this.memberRole = memberRole;
        this.gender = gender;
        this.status = status;
    }

    public Member toEntity() {
        return Member.builder().build();
    }
}
