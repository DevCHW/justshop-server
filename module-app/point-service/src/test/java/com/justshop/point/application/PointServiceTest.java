package com.justshop.point.application;

import com.justshop.core.kafka.message.point.PointEventCreate;
import com.justshop.point.IntegrationTestSupport;
import com.justshop.point.domain.entity.PointEventHistory;
import com.justshop.point.domain.repository.PointEventHistoryRepository;
import com.justshop.point.infrastructure.kafka.producer.PointEventHistoryCreateProducer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Transactional
class PointServiceTest extends IntegrationTestSupport {

    @Autowired
    private PointService pointService;

    @Autowired
    private PointEventHistoryRepository pointEventHistoryRepository;

    @MockBean
    private PointEventHistoryCreateProducer pointEventHistoryCreateProducer;

    @DisplayName("포인트 변동 양, 회원 ID, 포인트 이벤트 메세지를 입력받아서 포인트를 차감한다.")
    @Test
    void decreasePoint_success() {
        // given
        Long amount = 1000L;
        Long memberId = 1L;
        String pointEventMessage = "Test Decrease";

        // when
        Long savedHistoryId = pointService.decreasePoint(amount, memberId, pointEventMessage);
        PointEventHistory savedHistory = pointEventHistoryRepository.findById(savedHistoryId).get();

        // then
        assertThat(savedHistory.getAmount()).isEqualTo(amount);
        assertThat(savedHistory.getMemberId()).isEqualTo(memberId);
        assertThat(savedHistory.getEventMessage()).isEqualTo(pointEventMessage);
    }

    @DisplayName("포인트 변동 양, 회원 ID, 포인트 이벤트 메세지를 입력받아서 포인트를 적립한다.")
    @Test
    void addPoint_success() {
        // given
        Long amount = 1000L;
        Long memberId = 1L;
        String pointEventMessage = "Test Increase";

        PointEventCreate message = new PointEventCreate();
        BDDMockito.given(pointEventHistoryCreateProducer.send(any(PointEventCreate.class)))
                .willReturn(message);

        // when
        Long savedHistoryId = pointService.addPoint(amount, memberId, pointEventMessage);
        PointEventHistory savedHistory = pointEventHistoryRepository.findById(savedHistoryId).get();

        // then
        verify(pointEventHistoryCreateProducer, times(1)).send(any(PointEventCreate.class));
        assertThat(savedHistory.getAmount()).isEqualTo(amount);
        assertThat(savedHistory.getMemberId()).isEqualTo(memberId);
        assertThat(savedHistory.getEventMessage()).isEqualTo(pointEventMessage);
    }

}