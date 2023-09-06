package com.justshop.core.product.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OptionType {

    COLOR("색상"), SIZE("사이즈");

    private final String description;

}
