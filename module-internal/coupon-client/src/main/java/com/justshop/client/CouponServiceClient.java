package com.justshop.client;

import com.justshop.client.dto.CouponResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "coupon-service")
// TODO: API Gateway 로드밸런싱 적용시 url 삭제
@FeignClient(name = "coupon-service", url = "http://127.0.0.1:8083/api/v1/internal/coupons")
public interface CouponServiceClient {

    @GetMapping("/{couponId}")
    CouponResponse getCoupon(@PathVariable("couponId") Long couponId);

}
