package com.justshop.member.entity;

import com.justshop.core.error.ErrorCode;
import com.justshop.core.exception.BusinessException;
import com.justshop.member.entity.enums.Gender;
import com.justshop.member.entity.enums.MemberStatus;
import com.justshop.member.entity.enums.Role;
import com.justshop.jpa.entity.BaseEntity;
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

    // 닉네임 변경
    public void changeNickname(String nickname) {
        if (!this.nickname.equals(nickname)) {
            this.nickname = nickname;
        }
    }

    // 포인트 적립
    public void addPoint(Long amount) {
        this.point += amount;
    }

    // 포인트 차감
    public void decreasePoint(Long amount) {
        if (point < amount) {
            throw new BusinessException(ErrorCode.NOT_ENOUGH_POINT, "회원의 보유 포인트가 부족하여 포인트를 차감할 수 없습니다.");
        }
        this.point -= amount;
    }

}
