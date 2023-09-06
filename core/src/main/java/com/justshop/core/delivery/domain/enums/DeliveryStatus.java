package com.justshop.core.delivery.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryStatus {

    READY("배송준비중"),
    DELIVERING("배송중"),
    DELAY("배송지연"),
    COMPLETE("배송완료");

    private final String description;

}
