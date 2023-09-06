package com.justshop.core.payment.domain;

import com.justshop.core.BaseEntity;
import com.justshop.core.member.domain.Member;
import com.justshop.core.order.domain.Order;
import com.justshop.core.payment.domain.enums.PaymentGroup;
import com.justshop.core.payment.domain.enums.PaymentStatus;
import com.justshop.core.payment.domain.enums.PaymentType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Payment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 결제 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 회원 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order; // 주문 ID

    @Enumerated(EnumType.STRING)
    private PaymentGroup group; // 결제 종류

    @Enumerated(EnumType.STRING)
    private PaymentType type; // 결제 수단

    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // 결제 상태

    private int amount; // 결제 금액

}
