package com.justshop.apiserver.user.order.application.dto;

import lombok.Getter;

@Getter
public class CreateOrderResponse {

    private Long orderId;

    public CreateOrderResponse(Long orderId) {
        this.orderId = orderId;
    }

    public static CreateOrderResponse from(Long orderId) {
        return new CreateOrderResponse(orderId);
    }
}
