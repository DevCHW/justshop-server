package com.justshop.payment.domain.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentGroup {

    CARD("카드");

    private final String description;
}
