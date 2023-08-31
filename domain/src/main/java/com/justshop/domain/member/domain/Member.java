package com.justshop.domain.member.domain;

import com.justshop.domain.BaseEntity;
import com.justshop.domain.member.domain.enumerated.Gender;
import com.justshop.domain.member.domain.enumerated.MemberStatus;
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

    @Column(unique = true)
    private String loginId;

    private String password;

    private String name;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    private boolean allowToMarketingNotification;

    @Builder
    public Member(String loginId, String password, String name, String email, Gender gender, LocalDate birthday, MemberStatus status, boolean allowToMarketingNotification) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.birthday = birthday;
        this.status = status;
        this.allowToMarketingNotification = allowToMarketingNotification;
    }
}
