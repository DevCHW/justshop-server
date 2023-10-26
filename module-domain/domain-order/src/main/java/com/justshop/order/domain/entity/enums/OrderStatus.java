package com.justshop.order.domain.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    INIT("주문 생성"),
    ORDERED("주문완료"),
    CANCEL("주문취소"),
    COMPLETE("처리완료"),
    FAIL("주문 실패");

    private final String description;
}
