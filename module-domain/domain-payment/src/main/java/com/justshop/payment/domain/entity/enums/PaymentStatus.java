package com.justshop.payment.domain.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {

    WAIT("결제대기"), COMPLETE("결제완료");

    private final String description;

}
