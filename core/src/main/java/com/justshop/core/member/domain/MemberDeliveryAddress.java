package com.justshop.core.member.domain;

import com.justshop.core.common.converter.BooleanToYNConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberDeliveryAddress {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 회원_배송지 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 회원 ID

    @Convert(converter = BooleanToYNConverter.class)
    private boolean basicYn; // 기본배송지 여부

    // 주소 관련
    private String siDo; // 시/도
    private String guGun; // 구/군
    private String street; // 도로명 주소
    private String detailAddress; // 상세 주소
    private String zipcode; // 우편번호

    private String requestContent; // 요청사항
}
