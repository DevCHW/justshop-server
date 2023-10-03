package com.justshop.order.domain.entity;

import com.justshop.jpa.entity.BaseEntity;
import com.justshop.order.domain.entity.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId; // 주문자 ID
    private Long orderPrice; // 주문 금액
    private Long discountAmount; // 할인 금액
    private Long payAmount; // 최종 금액

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Builder
    public Order(Long memberId, Long orderPrice, Long discountAmount, Long payAmount, OrderStatus status) {
        this.memberId = memberId;
        this.orderPrice = orderPrice;
        this.discountAmount = discountAmount;
        this.payAmount = payAmount;
        this.status = status;
    }

    public void add(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
    }

}
