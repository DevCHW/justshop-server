package com.justshop.product.domain.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SellingStatus {
    SOLD_OUT("품절"), SELLING("판매중"), STOP("판매 중지");

    private final String description;
}
