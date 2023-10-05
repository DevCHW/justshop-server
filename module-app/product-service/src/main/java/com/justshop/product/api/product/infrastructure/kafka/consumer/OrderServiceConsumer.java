package com.justshop.product.api.product.infrastructure.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.justshop.core.error.ErrorCode;
import com.justshop.core.exception.BusinessException;
import com.justshop.core.kafka.message.Topics;
import com.justshop.core.kafka.message.order.OrderCreate;
import com.justshop.product.api.product.application.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceConsumer {

    private final ProductService productService;

    @KafkaListener(topics = Topics.ORDER_CREATE)
    public void decreaseStock(String kafkaMessage) {
        log.info("Kafka Message: -> {}", kafkaMessage);

        OrderCreate message;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            message = objectMapper.readValue(kafkaMessage, OrderCreate.class);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.JsonParsingError);
        }

        productService.decreaseStock(message);
    }
}
