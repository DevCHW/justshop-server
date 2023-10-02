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

    private String email; //이메일
    private String password; //비밀번호
    private String name; //이름
    private String nickname; //닉네임
    private int point; //포인트
    private LocalDate birthday; //생년월일

    @Enumerated(EnumType.STRING)
    private Role memberRole; //권한

    @Enumerated(EnumType.STRING)
    private Gender gender; //성별

    @Enumerated(EnumType.STRING)
    private MemberStatus status; //상태

    @Builder
    public Member(String email, String password, String name, String nickname, int point, LocalDate birthday, Role memberRole, Gender gender, MemberStatus status) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.point = point;
        this.birthday = birthday;
        this.memberRole = memberRole;
        this.gender = gender;
        this.status = status;
    }

    // 비밀번호 변경
    public void changePassword(String password) {
        this.password = password;
    }

}
