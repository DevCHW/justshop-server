package com.justshop.core.kafka.message.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderFailReason {
    NOT_ENOUGH_STOCK("재고 부족으로 주문에 실패하였습니다.");

    private final String description;
}
