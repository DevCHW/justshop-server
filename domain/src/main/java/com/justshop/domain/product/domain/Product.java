package com.justshop.domain.product.domain;

import com.justshop.domain.BaseEntity;
import com.justshop.domain.product.domain.enumerated.ProductSellingStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productNumber;

    private String name;

    @Enumerated(EnumType.STRING)
    private ProductSellingStatus sellingStatus;

    private int price;

    private int stockQuantity;

    @Builder
    public Product(String productNumber, String name, ProductSellingStatus sellingStatus, int price, int stockQuantity) {
        this.productNumber = productNumber;
        this.name = name;
        this.sellingStatus = sellingStatus;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

}
