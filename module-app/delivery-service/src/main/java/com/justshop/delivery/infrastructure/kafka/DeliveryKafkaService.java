package com.justshop.delivery.infrastructure.kafka;

import com.justshop.core.kafka.message.order.OrderCreate;
import com.justshop.delivery.domain.entity.Address;
import com.justshop.delivery.domain.entity.Delivery;
import com.justshop.delivery.domain.entity.enums.DeliveryStatus;
import com.justshop.delivery.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeliveryKafkaService {

    private final DeliveryRepository deliveryRepository;

    @Transactional
    public Long create(OrderCreate message) {
        Delivery delivery = Delivery.builder()
                .orderId(message.getOrderId())
                .status(DeliveryStatus.WAIT)
                .trackingNumber(null)
                .address(new Address(message.getStreet(), message.getCity(), message.getZipcode()))
                .period(null)
                .build();

        return deliveryRepository.save(delivery).getId();
    }
}
