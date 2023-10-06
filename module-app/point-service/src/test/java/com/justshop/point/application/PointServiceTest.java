package com.justshop.point.application;

import com.justshop.point.domain.entity.PointEventHistory;
import com.justshop.point.domain.repository.PointEventHistoryRepository;
import com.justshop.point.infrastructure.kafka.producer.PointEventHistoryCreateProducer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PointServiceTest {

    @Autowired
    private PointService pointService;

    @Autowired
    private PointEventHistoryRepository pointEventHistoryRepository;

    @Autowired
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
        Assertions.assertThat(savedHistory.getAmount()).isEqualTo(amount);
        Assertions.assertThat(savedHistory.getMemberId()).isEqualTo(memberId);
        Assertions.assertThat(savedHistory.getEventMessage()).isEqualTo(pointEventMessage);
    }

    @DisplayName("포인트 변동 양, 회원 ID, 포인트 이벤트 메세지를 입력받아서 포인트를 적립한다.")
    @Test
    void addPoint_success() {
        // given
        Long amount = 1000L;
        Long memberId = 1L;
        String pointEventMessage = "Test Increase";

        // when
        Long savedHistoryId = pointService.addPoint(amount, memberId, pointEventMessage);
        PointEventHistory savedHistory = pointEventHistoryRepository.findById(savedHistoryId).get();

        // then
        Assertions.assertThat(savedHistory.getAmount()).isEqualTo(amount);
        Assertions.assertThat(savedHistory.getMemberId()).isEqualTo(memberId);
        Assertions.assertThat(savedHistory.getEventMessage()).isEqualTo(pointEventMessage);
    }

}