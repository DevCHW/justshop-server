package com.justshop.core.member.command;

import com.justshop.core.member.domain.Member;
import com.justshop.core.member.domain.PointEventHistory;
import com.justshop.core.member.domain.enums.PointEventType;
import com.justshop.core.member.domain.repository.PointEventHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PointManager {

    private final PointEventHistoryRepository pointEventHistoryRepository;

    // 포인트 이벤트 정책
    public static final int NEW_MEMBER_ADD_POINT_AMOUNT = 5000;

    public static final String NEW_MEMBER_ADD_POINT_MESSAGE = "신규 회원가입 축하 포인트 적립";
    public static final String USE_MESSAGE = "포인트 사용";
    public static final String APPEND_MESSAGE = "포인트 적립";

    // 적립
    public PointEventHistory append(Member member, int amount, String message) {
        member.addPoint(amount);

        PointEventHistory history = PointEventHistory.builder()
                .member(member)
                .type(PointEventType.ADD)
                .amount(amount)
                .eventMessage(message)
                .remainingPoint(member.getPoint())
                .build();

        return pointEventHistoryRepository.save(history);
    }

    // 사용
    public PointEventHistory use(Member member, int amount, String message) {
        if (member.getPoint() < amount) { // TODO : 포인트 부족 오류 클래스 만들기, 테스트
            throw new RuntimeException("포인트가 부족합니다.");
        }

        member.usePoint(amount);

        PointEventHistory history = PointEventHistory.builder()
                .member(member)
                .type(PointEventType.ADD)
                .amount(amount)
                .eventMessage(message)
                .remainingPoint(member.getPoint())
                .build();

        return pointEventHistoryRepository.save(history);
    }

}
