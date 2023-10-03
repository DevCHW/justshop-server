package com.justshop.order.domain.entity;

import com.justshop.jpa.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderProduct extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Long productId; // 상품 ID
    private Long productOptionId; // 상품 옵션 ID
    private Long quantity; // 주문 수량

    @Builder
    public OrderProduct(Long productId, Long productOptionId, Long quantity) {
        this.productId = productId;
        this.productOptionId = productOptionId;
        this.quantity = quantity;
    }
}
