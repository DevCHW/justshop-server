package com.justshop.product.api.product.application.dto.response;

import com.justshop.product.domain.entity.Product;
import com.justshop.product.domain.entity.enums.Gender;
import com.justshop.product.domain.entity.enums.SellingStatus;
import lombok.Builder;

// TODO : 필드 정의하기
public class ProductResponse {

    private Long productId; // 상품 Id
    private String name; // 상품 이름
    private Integer price; // 상품 가격
    private Long salesQuantity; // 판매 수량
    private Long likeCount; // 좋아요 수
    private Long reviewCount; // 상품 리뷰 수
    private SellingStatus status; //상품 판매상태
    private Gender gender; // 성별

    @Builder
    private ProductResponse(Long productId, String name, Integer price, Long salesQuantity, Long likeCount, Long reviewCount, SellingStatus status, Gender gender) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.salesQuantity = salesQuantity;
        this.likeCount = likeCount;
        this.reviewCount = reviewCount;
        this.status = status;
        this.gender = gender;
    }

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .salesQuantity(product.getSalesQuantity())
                .likeCount(product.getLikeCount())
                .reviewCount(product.getReviewCount())
                .status(product.getStatus())
                .gender(product.getGender())
                .build();
    }
}
