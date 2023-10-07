package com.justshop.point.infrastructure.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.justshop.core.error.ErrorCode;
import com.justshop.core.exception.BusinessException;
import com.justshop.core.kafka.message.Topics;
import com.justshop.core.kafka.message.order.OrderCreate;
import com.justshop.point.application.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderServiceConsumer {

    private final PointService pointService;

    // 주문 완료 시 사용 포인트만큼 차감
    @KafkaListener(topics = Topics.ORDER_CREATE)
    public void usePoint(String kafkaMessage) {
        log.info("Kafka Message: -> {}", kafkaMessage);

        OrderCreate message;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            message = objectMapper.readValue(kafkaMessage, OrderCreate.class);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.JSON_PARSING_ERROR);
        }

        Long usePoint = message.getUsePoint(); // 사용 포인트
        Long memberId = message.getMemberId(); // 주문자 ID
        String pointEventMessage = "상품 구매 사용 포인트 차감"; // 포인트 이벤트 메세지

        if (!Objects.isNull(usePoint) && usePoint > 0 && !Objects.isNull(memberId)) {
            pointService.decreasePoint(usePoint, memberId, pointEventMessage);
        }
    }

}
