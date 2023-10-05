package com.justshop.point.domain.entity;

import com.justshop.point.domain.entity.enums.PointEventType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PointEventHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId; // 회원 ID
    @Enumerated(EnumType.STRING)
    private PointEventType type; // 적립 OR 차감
    private Long amount; // 변동 금액
    private String eventMessage; // 포인트 변동 이벤트 메세지

    @Builder
    public PointEventHistory(Long memberId, PointEventType type, Long amount, String eventMessage) {
        this.memberId = memberId;
        this.type = type;
        this.amount = amount;
        this.eventMessage = eventMessage;
    }
}
