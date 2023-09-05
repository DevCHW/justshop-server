package com.justshop.domain.order.domain.enumerated;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    ORDER("주문"),
    RECEIVED("주문접수"),
    CANCELED("주문취소"),
    COMPLETED("처리완료");

    private final String text;

}
