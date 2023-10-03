package com.justshop.order.api.order.infrastructure.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.justshop.error.ErrorCode;
import com.justshop.exception.BusinessException;
import com.justshop.order.api.order.infrastructure.kafka.dto.OrderCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderCreate send(String topic, OrderCreate order) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInString = "";

        try {
            jsonInString = objectMapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.JsonParsingError);
        }

        kafkaTemplate.send(topic, jsonInString);
        log.info("Order MicroService - Kafka 이벤트 메세지 발행 : {}", jsonInString);
        return order;
    }

}
