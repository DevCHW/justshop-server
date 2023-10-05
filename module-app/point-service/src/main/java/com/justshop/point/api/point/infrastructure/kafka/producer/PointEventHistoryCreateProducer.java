package com.justshop.point.api.point.infrastructure.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.justshop.core.error.ErrorCode;
import com.justshop.core.exception.BusinessException;
import com.justshop.core.kafka.message.Topics;
import com.justshop.core.kafka.message.point.PointEventCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PointEventHistoryCreateProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public PointEventCreate send(PointEventCreate pointEvent) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInString = "";

        try {
            jsonInString = objectMapper.writeValueAsString(pointEvent);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.JsonParsingError);
        }

        kafkaTemplate.send(Topics.POINT_EVENT_CREATE, jsonInString);
        log.info("Point MicroService - Kafka 이벤트 메세지 발행 : {}", jsonInString);
        return pointEvent;
    }
}
