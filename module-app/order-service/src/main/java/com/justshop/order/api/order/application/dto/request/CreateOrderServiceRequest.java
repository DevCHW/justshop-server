package com.justshop.order.api.order.application.dto.request;

import com.justshop.order.domain.entity.Order;
import com.justshop.order.domain.entity.OrderProduct;
import com.justshop.order.domain.entity.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateOrderServiceRequest {

    private Long memberId; // 주문자 ID
    private List<OrderProductRequest> orderProducts; //상품 ID, 옵션 ID 리스트
    private Long usePoint; //사용 포인트
    private Long couponId; //사용 쿠폰 ID

    @Builder
    public CreateOrderServiceRequest(Long memberId, List<OrderProductRequest> orderProducts, Long usePoint, Long couponId) {
        this.memberId = memberId;
        this.orderProducts = orderProducts;
        this.usePoint = usePoint;
        this.couponId = couponId;
    }

    @Getter
    public static class OrderProductRequest {
        private Long productId; //상품 ID
        private Long productOptionId; //상품 옵션 ID
        private Integer quantity; //주문 수량

        @Builder
        public OrderProductRequest(Long productId, Long productOptionId, Integer quantity) {
            this.productId = productId;
            this.productOptionId = productOptionId;
            this.quantity = quantity;
        }
    }

    public Order toEntity(Long orderPrice, Long payAmount) {
        Order order = Order.builder()
                .memberId(memberId)
                .orderPrice(orderPrice)
                .discountAmount(orderPrice - payAmount)
                .payAmount(payAmount)
                .status(OrderStatus.INIT)
                .build();

        this.orderProducts.forEach(
                op -> order.add(
                        new OrderProduct(order, op.getProductId(), op.getProductOptionId(), op.getQuantity().longValue())
                ));

        return order;
    }

}
