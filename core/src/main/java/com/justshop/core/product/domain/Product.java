package com.justshop.core.product.domain;

import com.justshop.core.BaseEntity;
import com.justshop.core.product.domain.enums.ProductGender;
import com.justshop.core.product.domain.enums.ProductSellingStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id

    private String name; // 상품명

    private String code; // 상품 코드

    @Enumerated(EnumType.STRING)
    private ProductSellingStatus sellingStatus; // 판매 상태

    private int price; // 가격

    private Long salesQuantity; // 판매 수량

    private String brandName; // 브랜드명

    @Enumerated(EnumType.STRING)
    private ProductGender gender; // 남/녀/전체 구분

    private String description; // 상품 설명

    // TODO : 재고 감소 로직 구현

    // TODO : 판매수량 1 증가 로직 구현
    
    // TODO : 상품번호 생성 로직 구현

}
