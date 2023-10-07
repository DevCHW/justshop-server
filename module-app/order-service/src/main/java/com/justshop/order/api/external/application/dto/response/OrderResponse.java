package com.justshop.order.api.external.application.dto.response;

import com.justshop.order.domain.entity.Order;
import com.justshop.order.domain.entity.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderResponse {

    private Long memberId; // 회원 ID
    private Long orderPrice; // 주문 가격
    private Long discountAmount; // 할인 가격
    private Long payAmount; // 결제 금액
    private OrderStatus status; // 주문 상태
    private List<OrderProductInfo> orderProducts; //주문 상품 정보

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

        private Long productId;// 상품 ID
        private Long productOptionId; // 상품 옵션 ID
        private Long quantity; // 주문 수량
        private Long productPrice; // 상품 옵션 가격
        private String productImagePath; // 상품 이미지

        @Builder
        public OrderProductInfo(Long productId, Long productOptionId, Long quantity, Long productPrice, String productImagePath) {
            this.productId = productId;
            this.productOptionId = productOptionId;
            this.quantity = quantity;
            this.productPrice = productPrice;
            this.productImagePath = productImagePath;
        }
    }

}
