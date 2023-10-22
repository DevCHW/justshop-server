package com.justshop.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponResponse {

    private Long id;
    private Long memberId;
    private int discountRate;
}
