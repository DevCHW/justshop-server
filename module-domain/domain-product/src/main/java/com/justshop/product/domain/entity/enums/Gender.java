package com.justshop.product.domain.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    MAN("남성용"), WOMEN("여성용"), FREE("남여공용");

    private final String description;
}
