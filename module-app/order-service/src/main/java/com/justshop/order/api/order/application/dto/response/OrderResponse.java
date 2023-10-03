package com.justshop.order.api.order.application.dto.response;

import com.justshop.order.domain.entity.Order;

public class OrderResponse {

    private String memberId; // 회원 ID
    private String orderPrice; // 주문 가격
    private String discountAmount; // 할인 가격
    private String payAmount; // 결제 금액
    private String status; // 주문 상태
    private String productId; // 상품 ID
    private String productOptionId; // 상품 옵션 ID
    private String quantity; // 주문 수량
    private String productPrice; // 상품 옵션 가격

    public static OrderResponse from(Order order) {
        return null;
    }

}
