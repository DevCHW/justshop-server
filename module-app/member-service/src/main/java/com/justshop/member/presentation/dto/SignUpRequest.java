package com.justshop.member.presentation.dto;

import com.justshop.member.entity.enums.Gender;
import com.justshop.member.entity.enums.MemberStatus;
import com.justshop.member.entity.enums.Role;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SignUpRequest {

    // TODO: Member 도메인 확정시 Validation 추가하기.
    private String loginId;
    private String password;
    private String name;
    private String nickname;
    private int point;
    private LocalDate birthday;
    private Role memberRole;
    private Gender gender;
    private MemberStatus status;

}
