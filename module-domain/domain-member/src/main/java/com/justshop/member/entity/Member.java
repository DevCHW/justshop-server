package com.justshop.member.entity;

import com.justshop.jpa.entity.BaseEntity;
import com.justshop.member.entity.enums.Gender;
import com.justshop.member.entity.enums.MemberStatus;
import com.justshop.member.entity.enums.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;
    private String password;
    private String name;
    private String nickname;
    private int point;
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private Role memberRole;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Builder
    public Member(String loginId, String password, String name, String nickname, int point, LocalDate birthday, Role memberRole, Gender gender, MemberStatus status) {
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
}
