package com.justshop.core.order.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    ORDER("주문"),
    CANCELED("주문취소"),
    COMPLETED("처리완료");

    private final String description;

}
