package com.justshop.payment.domain.entity;

import com.justshop.payment.domain.entity.enums.PaymentGroup;
import com.justshop.payment.domain.entity.enums.PaymentStatus;
import com.justshop.payment.domain.entity.enums.PaymentType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId; //결제 회원 ID

    @Enumerated(EnumType.STRING)
    private PaymentGroup paymentGroup; //결제그룹

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType; //결제타입

    @Enumerated(EnumType.STRING)
    private PaymentStatus status; //결제상태

    private Integer amount; //결제금액

}
