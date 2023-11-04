package com.justshop.delivery.domain.entity;

import com.justshop.delivery.domain.entity.enums.DeliveryStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId; //주문 ID

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; //배송 상태
    private String trackingNumber; //송장번호

    @Embedded
    private Address address; //주소

    @Embedded
    private Period period; // 배송 기간

    @Builder
    public Delivery(Long orderId, DeliveryStatus status, String trackingNumber, Address address, Period period) {
        this.orderId = orderId;
        this.status = status;
        this.trackingNumber = trackingNumber;
        this.address = address;
        this.period = period;
    }
}
