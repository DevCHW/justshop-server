package com.justshop.product.api.external.application.dto.response;

import com.justshop.product.domain.entity.Product;
import com.justshop.product.domain.entity.enums.Color;
import com.justshop.product.domain.entity.enums.Gender;
import com.justshop.product.domain.entity.enums.SellingStatus;
import com.justshop.product.domain.entity.enums.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String detail; // 상품 설명
    private List<ProductOption> productOption; // 상품 옵션 List
    private List<ProductImage> images; //상품 이미지 List

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

    @Getter
    @NoArgsConstructor
    public static class ProductImage {
        private Long productImageId; //상품 이미지 ID
        private String basicYn; //기본이미지 여부
        private String saveFileName; // 저장 파일명
        private String originFileName; // 원래 파일명
        private Long fileId; // 파일 ID

        @Builder
        public ProductImage(Long productImageId, String basicYn, String saveFileName, String originFileName, Long fileId) {
            this.productImageId = productImageId;
            this.basicYn = basicYn;
            this.saveFileName = saveFileName;
            this.originFileName = originFileName;
            this.fileId = fileId;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ProductOption {
        private Long productOptionId; // 상품 옵션 ID
        private Size size; // 사이즈
        private Color color; // 색상
        private String etc; // 기타
        private String additionalPrice; //추가금액
        private String stockQuantity; //재고수량
    }

}
