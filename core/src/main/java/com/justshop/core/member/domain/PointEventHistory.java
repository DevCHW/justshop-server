package com.justshop.core.member.domain;

import com.justshop.core.member.domain.enums.PointEventType;
import com.justshop.core.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PointEventHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 포인트 변동내역 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 회원 ID

    private int amount; // 변동 금액

    @Enumerated(EnumType.STRING)
    private PointEventType type; // 포인트 이벤트 타입 (ADD, DEDUCTION)

    private String eventMessage; // 이벤트 메세지

    private int remainingPoint; // 잔여 포인트

    @Builder
    public PointEventHistory(Member member, int amount, PointEventType type, String eventMessage, int remainingPoint) {
        this.member = member;
        this.amount = amount;
        this.type = type;
        this.eventMessage = eventMessage;
        this.remainingPoint = remainingPoint;
    }

}
