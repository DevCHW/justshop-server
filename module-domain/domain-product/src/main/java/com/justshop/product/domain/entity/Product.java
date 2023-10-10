package com.justshop.product.domain.entity;

import com.justshop.jpa.entity.BaseEntity;
import com.justshop.product.domain.entity.enums.Color;
import com.justshop.product.domain.entity.enums.Gender;
import com.justshop.product.domain.entity.enums.SellingStatus;
import com.justshop.product.domain.entity.enums.Size;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductOption> productOptions = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductCategory> productCategories = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> productImages = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_detail_id")
    private ProductDetail productDetail;

    @Builder
    public Product(String name, Integer price, Long salesQuantity, Long likeCount,
                   Long reviewCount, SellingStatus status, Gender gender) {
        this.name = name;
        this.price = price;
        this.salesQuantity = salesQuantity;
        this.likeCount = likeCount;
        this.reviewCount = reviewCount;
        this.status = status;
        this.gender = gender;
    }

    public void addProductCategory(Long categoryId) {
        this.productCategories.add(new ProductCategory(this, categoryId));
    }

    public void addProductOption(Size productSize, Color color, String etc, int additionalPrice, int stockQuantity) {
        this.productOptions.add(ProductOption.builder()
                .product(this)
                .productSize(productSize)
                .color(color)
                .etc(etc)
                .additionalPrice(additionalPrice)
                .stockQuantity(stockQuantity)
                .build());
    }

    public void addProductImage(Long fileId, Boolean basicYn) {
        this.productImages.add(new ProductImage(this, fileId, basicYn));
    }

    public void addProductDetail(String description) {
        this.productDetail = new ProductDetail(description);
    }
}
