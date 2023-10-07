package com.justshop.order.api.external.presentation.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class CreateOrderRequest {

    @NotNull(message = "주문자 ID는 필수입니다.")
    private Long memberId; // 주문자 ID

    @NotNull(message = "주문자 ID는 필수입니다.")
    private List<OrderProductRequest> orderProducts; //상품 ID, 옵션 ID 리스트

    @NotNull(message = "사용 포인트값은 필수입니다.")
    @Min(value = 0, message = "포인트는 음수가 될 수 없습니다.")
    private Long usePoint; //사용 포인트
    private Long couponId; //사용 쿠폰 ID

    @Builder
    public CreateOrderRequest(Long memberId, List<OrderProductRequest> orderProducts, Long usePoint, Long couponId) {
        this.memberId = memberId;
        this.orderProducts = orderProducts;
        this.usePoint = usePoint;
        this.couponId = couponId;
    }

    @Getter
    @NoArgsConstructor
    public static class OrderProductRequest {
        @NotNull(message = "상품 ID는 필수입니다.")
        private Long productId; //상품 ID

        @NotNull(message = "상품 옵션 ID는 필수입니다.")
        private Long productOptionId; //상품 옵션 ID

        @Min(value = 1, message = "주문 수량은 1 이상이어야 합니다.")
        @NotNull(message = "주문 수량은 필수입니다.")
        private Integer quantity; // 주문 수량

        public OrderProductRequest(Long productId, Long productOptionId, Integer quantity) {
            this.productId = productId;
            this.productOptionId = productOptionId;
            this.quantity = quantity;
        }
    }

}
