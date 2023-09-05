package com.justshop.domain.product.domain;

import com.justshop.domain.BaseEntity;
import com.justshop.domain.product.domain.enumerated.ProductSellingStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id

    @Column(name = "number")
    private String productNumber; // 상품 번호

    private String name; // 상품명

    @Enumerated(EnumType.STRING)
    private ProductSellingStatus sellingStatus; // 판매 상태

    private int price; // 가격

    private int stockQuantity;  // 재고 수량

    private Long salesQuantity; // 판매 수량

    private int wishCount;  //찜 수

    private List<ProductImageFile> productImages = new ArrayList<>(); // 상품 이미지 리스트

    @Builder
    public Product(String productNumber, String name, ProductSellingStatus sellingStatus, int price, int stockQuantity) {
        this.productNumber = productNumber;
        this.name = name;
        this.sellingStatus = sellingStatus;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    // TODO : 재고 감소 로직 구현

    // TODO : 판매수량 1 증가 로직 구현
    
    // TODO : 상품번호 부여 로직 구현

}
