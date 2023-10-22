package com.justshop.client;

import com.justshop.client.dto.CouponResponse;
import com.justshop.core.error.ErrorCode;
import com.justshop.core.exception.BusinessException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponReader {

    private final CouponServiceClient couponServiceClient;

    public CouponResponse read(Long couponId) {
        try {
            return couponServiceClient.getCoupon(couponId);
        } catch (FeignException e) {
            log.error(e.getMessage());
            throw new BusinessException(ErrorCode.COUPON_SERVER_ERROR);
        }
    }
}
