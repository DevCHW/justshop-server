package com.justshop.product.api.product.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.justshop.error.ErrorCode;
import com.justshop.exception.BusinessException;
import com.justshop.product.domain.entity.ProductOption;
import com.justshop.product.domain.repository.ProductOptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ProductOptionRepository productOptionRepository;

    @KafkaListener(topics = "order-create")
    public void decreaseStock(String kafkaMessage) {
        log.info("Kafka Message: -> {}", kafkaMessage);

        Map<Object, Object> message;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            message = objectMapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        
//        // TODO: 메세지 처리 로직 작성하기
//        Long productOptionId = (Long) message.get("productOptionId");
//        ProductOption productOption = productOptionRepository.findById(productOptionId)
//                .orElseThrow(() -> new BusinessException(ErrorCode.Product_OPTION_NOT_FOUND));
//
//        int orderQuantity = (Integer)message.get("qty");

    }

}
