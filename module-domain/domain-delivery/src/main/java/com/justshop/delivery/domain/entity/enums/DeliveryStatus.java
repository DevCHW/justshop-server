package com.justshop.delivery.domain.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryStatus {

    WAIT("배송대기"), PROCESS("진행중"), COMPLETE("배송완료");

    private final String description;
}
