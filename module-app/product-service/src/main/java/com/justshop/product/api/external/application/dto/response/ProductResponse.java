package com.justshop.product.api.external.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.justshop.product.domain.entity.Product;
import com.justshop.product.domain.entity.ProductCategory;
import com.justshop.product.domain.entity.ProductImage;
import com.justshop.product.domain.entity.ProductOption;
import com.justshop.product.domain.entity.enums.Color;
import com.justshop.product.domain.entity.enums.Gender;
import com.justshop.product.domain.entity.enums.SellingStatus;
import com.justshop.product.domain.entity.enums.Size;
import com.justshop.product.domain.repository.querydsl.dto.ProductDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.justshop.product.domain.repository.querydsl.dto.ProductDto.*;

@Getter
@NoArgsConstructor
public class ProductResponse {

    private Long productId; // 상품 Id

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long categoryId; // 상품 카테고리 ID

    private String name; // 상품 이름
    private Integer price; // 상품 가격
    private Long salesQuantity; // 판매 수량
    private Long likeCount; // 좋아요 수
    private Long reviewCount; // 상품 리뷰 수
    private SellingStatus status; //상품 판매상태
    private Gender gender; // 성별
    private String detail; // 상품 설명
    private List<ProductOptionResponse> options; // 상품 옵션 List
    private List<ProductImageResponse> images; //상품 이미지 List

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean likeExists; //좋아요 존재 여부

    @Builder
    private ProductResponse(Long productId, Long categoryId, String name, Integer price, Long salesQuantity, Long likeCount, Long reviewCount, SellingStatus status, Gender gender, String detail, List<ProductOptionResponse> options, List<ProductImageResponse> images, Boolean likeExists) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.salesQuantity = salesQuantity;
        this.likeCount = likeCount;
        this.reviewCount = reviewCount;
        this.status = status;
        this.gender = gender;
        this.detail = detail;
        this.options = options;
        this.images = images;
        this.likeExists = likeExists;
    }

    public static ProductResponse of(ProductDto productDto) {
        List<ProductOptionResponse> options = productDto.getProductOptions().stream()
                .map(option -> ProductOptionResponse.from(option))
                .collect(Collectors.toList());

        List<ProductImageResponse> images = productDto.getImages().stream()
                .map(productImageDto -> ProductImageResponse.from(productImageDto))
                .collect(Collectors.toList());

        return ProductResponse.builder()
                .productId(productDto.getProductId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .salesQuantity(productDto.getSalesQuantity())
                .likeCount(productDto.getLikeCount())
                .reviewCount(productDto.getReviewCount())
                .status(productDto.getStatus())
                .gender(productDto.getGender())
                .detail(productDto.getDetail())
                .images(images)
                .options(options)
                .build();
    }

    public static ProductResponse from(Product product) {
        List<ProductOptionResponse> productOptionResponses = product.getProductOptions().stream()
                .map(productOption -> ProductOptionResponse.from(productOption))
                .collect(Collectors.toList());

        List<ProductImageResponse> productImageResponses = product.getProductImages().stream()
                .map(productImage -> ProductImageResponse.from(productImage))
                .collect(Collectors.toList());

        return ProductResponse.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .salesQuantity(product.getSalesQuantity())
                .likeCount(product.getLikeCount())
                .reviewCount(product.getReviewCount())
                .status(product.getStatus())
                .gender(product.getGender())
                .detail(product.getProductDetail().getDescription())
                .options(productOptionResponses)
                .images(productImageResponses)
                .build();
    }

    public static ProductResponse from(ProductCategory productCategory) {
        List<ProductOptionResponse> productOptionResponses = productCategory.getProduct().getProductOptions().stream()
                .map(productOption -> ProductOptionResponse.from(productOption))
                .collect(Collectors.toList());

        List<ProductImageResponse> productImageResponses = productCategory.getProduct().getProductImages().stream()
                .map(productImage -> ProductImageResponse.from(productImage))
                .collect(Collectors.toList());

        return ProductResponse.builder()
                .productId(productCategory.getId())
                .categoryId(productCategory.getCategoryId())
                .name(productCategory.getProduct().getName())
                .price(productCategory.getProduct().getPrice())
                .salesQuantity(productCategory.getProduct().getSalesQuantity())
                .likeCount(productCategory.getProduct().getLikeCount())
                .reviewCount(productCategory.getProduct().getReviewCount())
                .status(productCategory.getProduct().getStatus())
                .gender(productCategory.getProduct().getGender())
                .detail(productCategory.getProduct().getProductDetail().getDescription())
                .options(productOptionResponses)
                .images(productImageResponses)
                .build();
    }

    @Getter
    @NoArgsConstructor
    public static class ProductImageResponse {
        private Long productImageId; //상품 이미지 ID
        private boolean basicYn; //기본이미지 여부
        private String saveFileName; // 저장 파일명
        private String originFileName; // 원래 파일명
        private String path; // 파일 경로

        @Builder
        public ProductImageResponse(Long productImageId, Long productId, boolean basicYn, String saveFileName,
                                    String originFileName, String path) {
            this.productImageId = productImageId;
            this.basicYn = basicYn;
            this.saveFileName = saveFileName;
            this.originFileName = originFileName;
            this.path = path;
        }

        public static ProductImageResponse from(ProductImageDto productImageDto) {
            return ProductImageResponse.builder()
                    .productImageId(productImageDto.getProductImageId())
                    .basicYn(productImageDto.isBasicYn())
                    .saveFileName(productImageDto.getSaveFileName())
                    .originFileName(productImageDto.getOriginFileName())
                    .path(productImageDto.getPath())
                    .build();
        }

        public static ProductImageResponse from(ProductImage productImage) {
            return ProductImageResponse.builder()
                    .productImageId(productImage.getId())
                    .basicYn(productImage.isBasicYn())
                    .saveFileName(productImage.getSaveFileName())
                    .originFileName(productImage.getOriginFileName())
                    .path(productImage.getPath())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ProductOptionResponse {
        private Long productOptionId; // 상품 옵션 ID
        private Size size; // 사이즈
        private Color color; // 색상
        private String etc; // 기타
        private Long additionalPrice; //추가금액
        private Long stockQuantity; //재고수량

        @Builder
        private ProductOptionResponse(Long productOptionId, Size size, Color color, String etc, Long additionalPrice, Long stockQuantity) {
            this.productOptionId = productOptionId;
            this.size = size;
            this.color = color;
            this.etc = etc;
            this.additionalPrice = additionalPrice;
            this.stockQuantity = stockQuantity;
        }

        public static ProductOptionResponse from(ProductOptionDto productOptionDto) {
            return ProductOptionResponse.builder()
                    .productOptionId(productOptionDto.getProductOptionId())
                    .size(productOptionDto.getSize())
                    .color(productOptionDto.getColor())
                    .etc(productOptionDto.getEtc())
                    .additionalPrice(productOptionDto.getAdditionalPrice())
                    .stockQuantity(productOptionDto.getStockQuantity())
                    .build();
        }

        public static ProductOptionResponse from(ProductOption productOption) {
            return ProductOptionResponse.builder()
                    .productOptionId(productOption.getId())
                    .size(productOption.getProductSize())
                    .color(productOption.getColor())
                    .etc(productOption.getEtc())
                    .additionalPrice((long) productOption.getAdditionalPrice())
                    .stockQuantity((long) productOption.getStockQuantity())
                    .build();
        }
    }

}
