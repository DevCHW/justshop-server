package com.justshop.point.infrastructure.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.justshop.core.kafka.message.member.MemberCreate;
import com.justshop.point.application.PointService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceConsumerTest {

    private PointService pointService = Mockito.mock(PointService.class);
    private MemberServiceConsumer memberServiceConsumer = new MemberServiceConsumer(pointService);

    @DisplayName("회원가입 이벤트 메세지를 받아 신규 회원가입 축하 포인트를 지급한다.")
    @Test
    void memberCreateEvent_success() throws JsonProcessingException {
        // given
        MemberCreate memberCreate = new MemberCreate(1L);
        ObjectMapper objectMapper = new ObjectMapper();
        String kafkaMessage = objectMapper.writeValueAsString(memberCreate);

        given(pointService.addPoint(anyLong(), anyLong(), anyString()))
                .willReturn(1L);

        // when
        Long savedEventHistory = memberServiceConsumer.memberCreateEvent(kafkaMessage);

        // then
        assertThat(savedEventHistory).isEqualTo(1L);
    }

}