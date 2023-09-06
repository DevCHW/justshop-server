package com.justshop.core.order.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    ORDER("주문"),
    CANCELED("주문취소"),
    DELIVERING("배송중"),
    COMPLETED("주문완료");

    private final String description;

}
