package com.justshop.coupon.api.internal.application;

import com.justshop.core.error.ErrorCode;
import com.justshop.core.exception.BusinessException;
import com.justshop.coupon.api.internal.application.dto.CouponResponse;
import com.justshop.domain.entity.Coupon;
import com.justshop.domain.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InternalCouponService {

    private final CouponRepository couponRepository;

    /* 쿠폰 정보 조회 */
    public CouponResponse getCoupon(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COUPON_NOT_FOUND));
        return CouponResponse.from(coupon);
    }
}
