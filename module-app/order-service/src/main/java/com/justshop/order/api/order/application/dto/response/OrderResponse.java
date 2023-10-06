package com.justshop.order.api.order.application.dto.response;

import com.justshop.order.domain.entity.Order;
import com.justshop.order.domain.entity.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderResponse {

    private Long memberId; // 회원 ID
    private Long orderPrice; // 주문 가격
    private Long discountAmount; // 할인 가격
    private Long payAmount; // 결제 금액
    private OrderStatus status; // 주문 상태
    private List<OrderProductInfo> orderProducts;

    @Builder
    public OrderResponse(Long memberId, Long orderPrice, Long discountAmount, Long payAmount, OrderStatus status) {
        this.memberId = memberId;
        this.orderPrice = orderPrice;
        this.discountAmount = discountAmount;
        this.payAmount = payAmount;
        this.status = status;
    }

    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .memberId(order.getMemberId())
                .orderPrice(order.getOrderPrice())
                .discountAmount(order.getDiscountAmount())
                .payAmount(order.getPayAmount())
                .status(order.getStatus())
                .build();
    }

    @Getter
    @NoArgsConstructor
    public static class OrderProductInfo {

        private String productId;// 상품 ID
        private String productOptionId; // 상품 옵션 ID
        private String quantity; // 주문 수량
        private String productPrice; // 상품 옵션 가격

        @Builder
        public OrderProductInfo(String productId, String productOptionId, String quantity, String productPrice) {
            this.productId = productId;
            this.productOptionId = productOptionId;
            this.quantity = quantity;
            this.productPrice = productPrice;
        }
    }

}
