package com.justshop.coupon.api.internal.presentation;

import com.justshop.coupon.api.internal.application.InternalCouponService;
import com.justshop.coupon.api.internal.application.dto.CouponResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/internal/coupons")
@RequiredArgsConstructor
public class InternalCouponController {

    private final InternalCouponService couponService;

    @GetMapping("/{couponId}")
    public CouponResponse getCoupon(@PathVariable Long couponId) {
        return couponService.getCoupon(couponId);

    }
}
