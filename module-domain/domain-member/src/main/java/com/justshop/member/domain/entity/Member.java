package com.justshop.member.domain.entity;

import com.justshop.core.error.ErrorCode;
import com.justshop.core.exception.BusinessException;
import com.justshop.member.domain.entity.enums.Gender;
import com.justshop.member.domain.entity.enums.MemberStatus;
import com.justshop.member.domain.entity.enums.Role;
import com.justshop.jpa.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email; //이메일

    @Column(unique = true)
    private String nickname; //닉네임

    private String password; //비밀번호
    private String name; //이름
    private int point; //포인트
    private LocalDate birthday; //생년월일

    @Enumerated(EnumType.STRING)
    private Role memberRole; //권한

    @Enumerated(EnumType.STRING)
    private Gender gender; //성별

    @Enumerated(EnumType.STRING)
    private MemberStatus status; //상태

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<DeliveryAddress> address = new ArrayList<>();

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

    // 연관관계 편의 메서드
    public void addDeliveryAddress(DeliveryAddress address) {
        this.address.add(address);
        address.addMember(this);
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

    // 기본배송지 반환
    public DeliveryAddress getBasicAddress() {
        return this.address.stream()
                .filter(address -> address.isBasicYn())
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.DELIVERY_NOT_FOUND));
    }

}
