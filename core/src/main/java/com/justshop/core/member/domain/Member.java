package com.justshop.core.member.domain;

import com.justshop.core.BaseEntity;
import com.justshop.core.common.converter.BooleanToYNConverter;
import com.justshop.core.member.domain.enums.Gender;
import com.justshop.core.member.domain.enums.MemberStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // member_id

    @Column(unique = true)
    private String loginId; // 로그인 ID

    private String password; // 로그인 패스워드

    @Column(unique = true)
    private String email; // 이메일

    private String name; // 이름
    
    private String nickname; // 닉네임
    
    private String phone; // 폰번호

    @Enumerated(EnumType.STRING)
    private Gender gender; // 성별 (MAN, WOMAN)

    private LocalDate birthday; // 생년월일

    @Enumerated(EnumType.STRING)
    private MemberStatus status; // 회원 상태 (ACTIVE, SECESSION)

    @Convert(converter = BooleanToYNConverter.class)
    private boolean allowToMarketingNotification; // 이메일 수신 동의여부

    private LocalDateTime lastPasswordChanged; // 마지막 비밀번호 변경일자

    private int point;

    @Builder
    public Member(String loginId, String password, String email, String name, String nickname, String phone, Gender gender, LocalDate birthday, MemberStatus status, boolean allowToMarketingNotification, LocalDateTime lastPasswordChanged, int point) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.gender = gender;
        this.birthday = birthday;
        this.status = status;
        this.allowToMarketingNotification = allowToMarketingNotification;
        this.lastPasswordChanged = lastPasswordChanged;
        this.point = point;
    }
}
