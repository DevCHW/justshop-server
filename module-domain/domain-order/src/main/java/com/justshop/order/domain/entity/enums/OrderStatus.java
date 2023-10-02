package com.justshop.order.domain.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    ORDER("주문"), COMPLETE("처리완료");

    private final String description;
}
