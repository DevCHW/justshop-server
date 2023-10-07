package com.justshop.member.infrastructure.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.justshop.core.error.ErrorCode;
import com.justshop.core.exception.BusinessException;
import com.justshop.core.kafka.message.Topics;
import com.justshop.core.kafka.message.point.PointEventCreate;
import com.justshop.member.api.external.application.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class PointServiceConsumer {

    private final MemberService memberService;

    // 포인트 변동내역 생성 시 포인트 업데이트
    @KafkaListener(topics = Topics.POINT_EVENT_CREATE)
    public void pointEventCreate(String kafkaMessage) {
        log.info("Kafka Message: -> {}", kafkaMessage);

        PointEventCreate message;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            message = objectMapper.readValue(kafkaMessage, PointEventCreate.class);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.JsonParsingError);
        }

        Long amount = message.getAmount(); //포인트 변동금액
        Long memberId = message.getMemberId(); //주문자 ID
        String type = message.getType(); //포인트 이벤트 타입

        if (!Objects.isNull(amount) && amount > 0 && !Objects.isNull(memberId)) {
            memberService.updatePoint(memberId, amount, type);
        }

    }

}
