package com.justshop.core.payment.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {

    INIT("결제생성"),
    WAIT("결제대기"),
    FAIL("결제실패"),
    REFUND("환불"),
    COMPLETED("결제완료");

    private final String description;

}
