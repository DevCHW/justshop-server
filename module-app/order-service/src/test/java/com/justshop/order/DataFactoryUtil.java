package com.justshop.order;

import com.justshop.order.domain.entity.Order;
import com.justshop.order.domain.entity.enums.OrderStatus;

public class DataFactoryUtil {

    private DataFactoryUtil() {
    }

    public static Order generateOrder() {
        return Order.builder()
                .memberId(1L)
                .orderPrice(1000000L)
                .discountAmount(10000L)
                .payAmount((1000000L - 10000L))
                .status(OrderStatus.ORDERED)
                .build();
    }

}
