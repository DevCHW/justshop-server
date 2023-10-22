package com.justshop.coupon.api.internal.application.dto;

import com.justshop.domain.entity.Coupon;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CouponResponse {

    private Long id;
    private Long memberId;
    private int discountRate;

    @Builder
    private CouponResponse(Long id, Long memberId, int discountRate) {
        this.id = id;
        this.memberId = memberId;
        this.discountRate = discountRate;
    }

    public static CouponResponse from(Coupon coupon) {
        return CouponResponse.builder()
                .id(coupon.getId())
                .memberId(coupon.getMemberId())
                .discountRate(coupon.getDiscountRate())
                .build();
    }
}
