package com.justshop.point.api.point.application;

import com.justshop.point.api.point.infrastructure.kafka.producer.PointEventHistoryCreateProducer;
import com.justshop.core.kafka.message.point.PointEventCreate;
import com.justshop.point.domain.entity.PointEventHistory;
import com.justshop.point.domain.entity.enums.PointEventType;
import com.justshop.point.domain.repository.PointEventHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointService {

    private final PointEventHistoryRepository pointEventHistoryRepository;
    private final PointEventHistoryCreateProducer pointEventHistoryCreateProducer;

    // 포인트 사용
    @Transactional
    public void usePoint(final Long amount, final Long memberId, final String pointEventMessage) {
        PointEventHistory pointEvent = PointEventHistory.builder()
                .memberId(memberId)
                .type(PointEventType.DEDUCTION)
                .amount(amount)
                .eventMessage(pointEventMessage)
                .build();

        PointEventHistory savedHistory = pointEventHistoryRepository.save(pointEvent);

        // 이벤트 메세지 발행
        PointEventCreate message = generatePointEventCreateMessage(savedHistory);
        pointEventHistoryCreateProducer.send(message);
    }

    // 포인트 적립
    @Transactional
    public void add(final Long memberId, final Long amount, final String pointEventMessage) {
        PointEventHistory pointEvent = PointEventHistory.builder()
                .memberId(memberId)
                .type(PointEventType.ADD)
                .amount(amount)
                .eventMessage(pointEventMessage)
                .build();

        PointEventHistory savedHistory = pointEventHistoryRepository.save(pointEvent);

        // 이벤트 메세지 발행
        PointEventCreate message = generatePointEventCreateMessage(savedHistory);
        pointEventHistoryCreateProducer.send(message);
    }

    // PointEvent 생성 메세지
    private PointEventCreate generatePointEventCreateMessage(final PointEventHistory pointEventHistory) {
        return PointEventCreate.builder()
                .pointEventHistoryId(pointEventHistory.getId())
                .memberId(pointEventHistory.getMemberId())
                .type(pointEventHistory.getType().name())
                .amount(pointEventHistory.getAmount())
                .build();
    }
}
