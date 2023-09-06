package com.justshop.core.delivery.domain;

import com.justshop.core.BaseEntity;
import com.justshop.core.delivery.domain.enums.DeliveryStatus;
import com.justshop.core.order.domain.Order;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Delivery extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 배송 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order; // 주문 ID

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // 배송 상태

    private String trackingNumber; // 송장 번호

    private LocalDate startDate; // 배송 시작일자

    private LocalDate endDate; // 배송 완료일자

    // 주소 관련
    private String siDo; // 시/도
    private String guGun; // 구/군
    private String street; // 도로명 주소
    private String detailAddress; // 상세 주소
    private String zipcode; // 우편번호

}
