package com.justshop.apiserver.api.user.order.service.dto;

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
