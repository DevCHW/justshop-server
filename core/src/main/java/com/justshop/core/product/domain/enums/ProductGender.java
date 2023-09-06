package com.justshop.core.product.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductGender {

    MAN("남성용"), WOMAN("여성용"), ALL("전체");

    private final String description;
}
