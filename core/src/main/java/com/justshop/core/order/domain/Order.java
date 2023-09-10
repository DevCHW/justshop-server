package com.justshop.core.order.domain;

import com.justshop.core.order.domain.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 주문 ID

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문 상태

    private int totalPrice; // 주문 금액

    private int discountAmount; // 할인 가격


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    private Long memberId;

    @Builder
    public Order(OrderStatus orderStatus, int totalPrice, List<OrderProduct> orderProducts, Long memberId, int discountAmount) {
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.orderProducts = orderProducts;
        this.memberId = memberId;
        this.discountAmount = discountAmount;
    }

}
