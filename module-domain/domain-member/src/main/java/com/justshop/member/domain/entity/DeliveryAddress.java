package com.justshop.member.domain.entity;

import com.justshop.core.error.ErrorCode;
import com.justshop.core.exception.BusinessException;
import com.justshop.jpa.converter.BooleanToYNConverter;
import com.justshop.jpa.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DeliveryAddress extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Convert(converter = BooleanToYNConverter.class)
    private boolean basicYn;

    @Embedded
    private Address address;

    @Builder
    public DeliveryAddress(Member member, boolean basicYn, Address address) {
        this.member = member;
        this.basicYn = basicYn;
        this.address = address;
    }

    // 연관관계 편의 메서드
    public void addMember(Member member) {
        this.member = member;
    }
    /* basicYn 변경 */
    public void changeBasicYn() {
        this.basicYn = this.basicYn?false:true;
    }

    /* 주소 변경 */
    public void changeAddress(Address address) {
        this.address = address;
    }
}
