package com.justshop.core.kafka.message.point;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PointEventCreate {

    private Long pointEventHistoryId; //포인트 변동 히스토리 ID
    private Long memberId; // 회원 ID
    private Long amount; // 변동 금액
    private String type; // 타입(적립, 차감)

    @Builder
    private PointEventCreate(Long pointEventHistoryId, Long memberId, String type, Long amount) {
        this.pointEventHistoryId = pointEventHistoryId;
        this.memberId = memberId;
        this.type = type;
        this.amount = amount;
    }
}
