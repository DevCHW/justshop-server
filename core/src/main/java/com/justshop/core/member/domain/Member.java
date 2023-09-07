package com.justshop.core.member.domain;

import com.justshop.core.BaseEntity;
import com.justshop.core.common.converter.BooleanToYNConverter;
import com.justshop.core.member.domain.enums.Gender;
import com.justshop.core.member.domain.enums.MemberStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String loginId;

    private String password;

    @Column(unique = true)
    private String email;

    private String name;

    @Column(unique = true)
    private String nickname;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthday;

    @ColumnDefault(value = "'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Convert(converter = BooleanToYNConverter.class)
    private boolean allowToMarketingNotification;

    private LocalDateTime lastPasswordChanged;

    @ColumnDefault(value = "0")
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

    // 비밀번호 암호화
    public void encryptPassword() {
        this.password = SHA256.encrypt(this.password);
    }

    // 포인트 적립
    public void addPoint(int amount) {
        this.point = point += amount;
    }

    // 포인트 사용
    public void usePoint(int amount) {
        this.point = point -= amount;
    }

}
