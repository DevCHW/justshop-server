package com.justshop.core.payment.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum PaymentGroup {

    CASH("현금"),
    CARD("카드"),
    ETC("기타"),
    EMPTY("없음");

    private final String description;


}
