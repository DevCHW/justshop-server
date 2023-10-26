package com.justshop.order.infrastructure.kafka.consume;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.justshop.core.kafka.message.Topics;
import com.justshop.core.kafka.message.order.OrderFail;
import com.justshop.order.infrastructure.kafka.consume.service.OrderConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductServiceConsumer {

    private final OrderConsumerService orderConsumerService;

    /* 재고부족, 주문 실패 */
    @KafkaListener(topics = Topics.ORDER_FAIL)
    public void orderFailEvent(String kafkaMessage) {
        log.info("Kafka Message: -> {}", kafkaMessage);

        OrderFail message;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            message = objectMapper.readValue(kafkaMessage, OrderFail.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        orderConsumerService.fail(message.getId(), message.getReason());
    }

}
