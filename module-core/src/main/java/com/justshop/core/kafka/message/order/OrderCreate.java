package com.justshop.core.kafka.message.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCreate {
    private Long orderId;   // 주문 ID
    private Long memberId;  // 주문자 ID
    private Long couponId;  // 사용 쿠폰 ID
    private Long usePoint;  // 사용 포인트
    private Long payAmount; // 최종 금액
    private String city; // 주문자 주소
    private String zipcode; // 주문자 주소
    private String street; // 주문자 주소
    private List<OrderQuantity> orderQuantities = new ArrayList<>(); // 주문수량 정보

    @Builder
    public OrderCreate(Long orderId, Long memberId, Long couponId,
                       Long usePoint, Long payAmount,
                       String city, String zipcode, String street,
                       List<OrderQuantity> orderQuantities) {
        this.orderId = orderId;
        this.memberId = memberId;
        this.street = street;
        this.zipcode = zipcode;
        this.city = city;
        this.couponId = couponId;
        this.usePoint = usePoint;
        this.payAmount = payAmount;
        this.orderQuantities = orderQuantities;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderQuantity {
        private Long productOptionId;
        private Integer quantity;
    }

}
