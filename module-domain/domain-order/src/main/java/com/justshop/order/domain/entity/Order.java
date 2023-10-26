package com.justshop.order.domain.entity;

import com.justshop.core.error.ErrorCode;
import com.justshop.core.exception.BusinessException;
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

    /* 연관관계 편의 메서드 */
    public void addOrderProduct(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
    }

    // 주문 취소
    public void cancel() {
        if (this.status.equals(OrderStatus.COMPLETE)) {
            throw new BusinessException(ErrorCode.ORDER_CANCEL_FAIL, "처리 완료된 주문건은 취소가 불가합니다.");
        }

        this.status = OrderStatus.CANCEL;
    }

    // 주문 실패
    public void fail() {
        this.status = OrderStatus.FAIL;

    }

    // 주문 성공
    public void success() {
        this.status = OrderStatus.ORDERED;
    }
}
