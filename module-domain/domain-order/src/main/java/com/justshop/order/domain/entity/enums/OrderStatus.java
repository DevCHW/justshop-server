package com.justshop.order.domain.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    INIT("결제대기"), ORDERED("주문완료"), CANCEL("주문취소"), COMPLETE("처리완료");

    private final String description;
}
