package com.justshop.product.infrastructure.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.justshop.core.error.ErrorCode;
import com.justshop.core.exception.BusinessException;
import com.justshop.core.kafka.message.Topics;
import com.justshop.core.kafka.message.order.OrderFail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderFailProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderFail send(OrderFail orderFail) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInString = "";

        try {
            jsonInString = objectMapper.writeValueAsString(orderFail);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.JSON_PARSING_ERROR);
        }

        kafkaTemplate.send(Topics.ORDER_FAIL, jsonInString);
        log.info("Order MicroService - Kafka 이벤트 메세지 발행 : {}", jsonInString);
        return orderFail;
    }
}
