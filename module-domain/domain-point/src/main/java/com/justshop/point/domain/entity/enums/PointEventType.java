package com.justshop.point.domain.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PointEventType {
    ADD("적립"), DEDUCTION("차감");

    private final String description;
}
