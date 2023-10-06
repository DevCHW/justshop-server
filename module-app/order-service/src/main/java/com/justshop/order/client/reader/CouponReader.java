package com.justshop.order.client.reader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class CouponReader {

    public int read(Long couponId) {
        if (!Objects.isNull(couponId)) {
            return 0;
        }

        // Feign Client로 쿠폰서비스에서 쿠폰을 조회한다.
        // 쿠폰을 찾지 못한다면 익셉션
        // 쿠폰 보유자가 요청된 사용자가 맞는지 확인한다. 다르다면 익셉션
        // 쿠폰의 할인율을 couponDiscountRate 변수에 담기.
        return 0;
    }
}
