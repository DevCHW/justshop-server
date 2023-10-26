package com.justshop.order.infrastructure.kafka.consume;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.justshop.core.kafka.message.Topics;
import com.justshop.core.kafka.message.order.OrderSuccess;
import com.justshop.order.infrastructure.kafka.consume.service.OrderConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentServiceConsumer {

    private final OrderConsumerService orderConsumerService;

    /* 주문 실패 */
    @KafkaListener(topics = Topics.ORDER_SUCCESS)
    public void orderFailEvent(String kafkaMessage) {
        log.info("Kafka Message: -> {}", kafkaMessage);

        OrderSuccess message;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            message = objectMapper.readValue(kafkaMessage, OrderSuccess.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        orderConsumerService.success(message.getId());
    }

}
