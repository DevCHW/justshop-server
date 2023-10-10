package com.justshop.product.domain.repository.querydsl.dto;

import com.justshop.product.domain.entity.Product;
import com.justshop.product.domain.entity.ProductImage;
import com.justshop.product.domain.entity.ProductOption;
import com.justshop.product.domain.entity.enums.Color;
import com.justshop.product.domain.entity.enums.Gender;
import com.justshop.product.domain.entity.enums.SellingStatus;
import com.justshop.product.domain.entity.enums.Size;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ProductDto {

    private Long productId; // 상품 Id
    private String name; // 상품 이름
    private Integer price; // 상품 가격
    private Long salesQuantity; // 판매 수량
    private Long likeCount; // 좋아요 수
    private Long reviewCount; // 상품 리뷰 수
    private SellingStatus status; //상품 판매상태
    private Gender gender; // 성별
    private String detail; // 상품 설명
    private List<ProductOptionDto> productOptions; // 상품 옵션 List
    private List<ProductImageDto> images; //상품 이미지 List

    @Builder
    public ProductDto(Long productId, String name, Integer price, Long salesQuantity, Long likeCount, Long reviewCount, SellingStatus status, Gender gender, String detail, List<ProductOptionDto> productOptions, List<ProductImageDto> images) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.salesQuantity = salesQuantity;
        this.likeCount = likeCount;
        this.reviewCount = reviewCount;
        this.status = status;
        this.gender = gender;
        this.detail = detail;
        this.productOptions = productOptions;
        this.images = images;
    }

    public static ProductDto of(Product product, List<ProductOptionDto> productOptions, List<ProductImageDto> images) {
        return ProductDto.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .salesQuantity(product.getSalesQuantity())
                .likeCount(product.getLikeCount())
                .reviewCount(product.getReviewCount())
                .status(product.getStatus())
                .gender(product.getGender())
                .detail(product.getProductDetail().getDescription())
                .productOptions(productOptions)
                .images(images)
                .build();
    }

    @Getter
    public static class ProductImageDto {
        private Long productImageId; //상품 이미지 ID
        private Long fileId; // 파일 ID
        private Long productId; // 상품 ID
        private boolean basicYn; // 기본이미지 여부

        @Builder
        private ProductImageDto(Long productImageId, Long fileId, Long productId, boolean basicYn) {
            this.productImageId = productImageId;
            this.fileId = fileId;
            this.productId = productId;
            this.basicYn = basicYn;
        }

        public static ProductImageDto from(ProductImage productImage) {
            return ProductImageDto.builder()
                    .productImageId(productImage.getId())
                    .basicYn(productImage.isBasicYn())
                    .fileId(productImage.getFileId())
                    .build();
        }
    }

    @Getter
    public static class ProductOptionDto {
        private Long productOptionId; // 상품 옵션 ID
        private Size size; // 사이즈
        private Color color; // 색상
        private String etc; // 기타
        private Long additionalPrice; //추가금액
        private Long stockQuantity; //재고수량

        @Builder
        private ProductOptionDto(Long productOptionId, Size size, Color color, String etc, Long additionalPrice, Long stockQuantity) {
            this.productOptionId = productOptionId;
            this.size = size;
            this.color = color;
            this.etc = etc;
            this.additionalPrice = additionalPrice;
            this.stockQuantity = stockQuantity;
        }

        public static ProductOptionDto from(ProductOption productOption) {
            return ProductOptionDto.builder()
                    .productOptionId(productOption.getId())
                    .size(productOption.getProductSize())
                    .color(productOption.getColor())
                    .etc(productOption.getEtc())
                    .additionalPrice(new Long(productOption.getAdditionalPrice()))
                    .stockQuantity(new Long(productOption.getStockQuantity()))
                    .build();
        }
    }
}
