package com.justshop.delivery.infrastructure.kafka;

import com.justshop.core.kafka.message.order.OrderCreate;
import com.justshop.delivery.IntegrationTestSupport;
import com.justshop.delivery.domain.entity.Delivery;
import com.justshop.delivery.domain.repository.DeliveryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

class DeliveryKafkaServiceTest extends IntegrationTestSupport {

    @Autowired
    private DeliveryKafkaService deliveryKafkaService;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @DisplayName("주문 생성 메세지를 받아서 배송정보를 생성한다.")
    @Test
    void create_success() {
        // given
        OrderCreate message = OrderCreate.builder()
                .orderId(1L)
                .memberId(1L)
                .street("test street")
                .zipcode("test zipcode")
                .city("test city")
                .build();

        // when
        Long id = deliveryKafkaService.create(message);
        flushAndClear();

        Delivery delivery = deliveryRepository.findById(id).get();

        // then
        assertThat(delivery.getId()).isEqualTo(id);
        assertThat(delivery.getAddress().getCity()).isEqualTo(message.getCity());
        assertThat(delivery.getAddress().getStreet()).isEqualTo(message.getStreet());
        assertThat(delivery.getAddress().getZipcode()).isEqualTo(message.getZipcode());
    }

}