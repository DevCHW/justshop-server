package com.justshop.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Coupon {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private int discountRate;

    public Coupon(Long memberId, int discountAmount) {
        this.memberId = memberId;
        this.discountRate = discountAmount;
    }

}
