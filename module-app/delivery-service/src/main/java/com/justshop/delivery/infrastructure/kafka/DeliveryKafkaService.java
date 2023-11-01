package com.justshop.delivery.infrastructure.kafka;

import com.justshop.core.kafka.message.order.OrderCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class DeliveryKafkaService {

    public Long create(OrderCreate message) {
        return null;
    }
}
