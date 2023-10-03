package com.justshop.order.domain.entity;

import com.justshop.order.domain.entity.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId; // 주문자 ID
    private Long orderPrice; // 주문 금액
    private Long discountAmount; // 할인 금액
    private Long payAmount; // 최종 금액

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태

    @Builder
    public Order(Long memberId, Long orderPrice, Long discountAmount, Long payAmount, OrderStatus status) {
        this.memberId = memberId;
        this.orderPrice = orderPrice;
        this.discountAmount = discountAmount;
        this.payAmount = payAmount;
        this.status = status;
    }
}
