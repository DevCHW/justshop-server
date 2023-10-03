package com.justshop.product.domain.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Color {

    BLACK("검정색"),
    WHITE("하얀색"),
    RED("빨간색"),
    ORANGE("주황색"),
    YELLOW("노란색"),
    GREEN("초록색"),
    BLUE("파란색"),
    INDIGO("남색"),
    VIOLET("보라색");

    private final String description;
}
