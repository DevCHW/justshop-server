package com.justshop.point.infrastructure.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.justshop.core.kafka.message.Topics;
import com.justshop.core.kafka.message.member.MemberCreate;
import com.justshop.point.application.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberServiceConsumer {

    private final PointService pointService;

    // 신규 회원 가입시 1000 포인트 지급
    @KafkaListener(topics = Topics.MEMBER_CREATE)
    public void memberCreateEvent(String kafkaMessage) {
        log.info("Kafka Message: -> {}", kafkaMessage);

        MemberCreate message;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            message = objectMapper.readValue(kafkaMessage, MemberCreate.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Long memberId = message.getMemberId(); // 회원 ID
        Long amount = 1000L; // 포인트 변동 양
        String pointEventMessage = "신규 회원 가입 축하 "+ amount +"포인트 적립"; // 포인트 이벤트 메세지
        pointService.add(memberId, amount, pointEventMessage);

    }
}
