package com.justshop.product.domain.entity;

import com.justshop.jpa.entity.BaseEntity;
import com.justshop.product.domain.entity.enums.Gender;
import com.justshop.product.domain.entity.enums.SellingStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // 상품명
    private Integer price; // 가격
    private Long salesQuantity; // 판매 수량
    private Long likeCount; // 좋아요 수
    private Long reviewCount; // 후기 수

    @Enumerated(EnumType.STRING)
    private SellingStatus status;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Builder
    public Product(String name, Integer price, Long salesQuantity, Long likeCount, Long reviewCount, SellingStatus status, Gender gender) {
        this.name = name;
        this.price = price;
        this.salesQuantity = salesQuantity;
        this.likeCount = likeCount;
        this.reviewCount = reviewCount;
        this.status = status;
        this.gender = gender;
    }

}
