package com.justshop.member.api.auth.infrastructure.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.justshop.core.error.ErrorCode;
import com.justshop.core.exception.BusinessException;
import com.justshop.core.kafka.message.Topics;
import com.justshop.core.kafka.message.member.MemberCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberCreateProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public MemberCreate send(Long memberId) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInString = "";

        MemberCreate memberCreate = new MemberCreate(memberId);

        try {
            jsonInString = objectMapper.writeValueAsString(memberCreate);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.JsonParsingError);
        }

        kafkaTemplate.send(Topics.MEMBER_CREATE, jsonInString);
        log.info("Member MicroService - Kafka 이벤트 메세지 발행 : {}", jsonInString);
        return memberCreate;
    }

}
