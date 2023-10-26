package com.justshop.core.kafka.message.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderFail {
    private Long id;
    private OrderFailReason reason;
}
