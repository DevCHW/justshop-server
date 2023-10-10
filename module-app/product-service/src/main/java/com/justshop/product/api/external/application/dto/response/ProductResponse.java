package com.justshop.product.api.external.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.justshop.product.client.response.FileResponse;
import com.justshop.product.domain.entity.enums.Color;
import com.justshop.product.domain.entity.enums.Gender;
import com.justshop.product.domain.entity.enums.SellingStatus;
import com.justshop.product.domain.entity.enums.Size;
import com.justshop.product.domain.repository.querydsl.dto.ProductDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.justshop.product.domain.repository.querydsl.dto.ProductDto.*;

@Getter
@NoArgsConstructor
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
    private List<ProductOption> options; // 상품 옵션 List
    private List<ProductImage> images; //상품 이미지 List

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean likeExists; //좋아요 존재 여부

    @Builder
    private ProductResponse(Long productId, String name, Integer price, Long salesQuantity, Long likeCount, Long reviewCount, SellingStatus status, Gender gender, String detail, List<ProductOption> options, List<ProductImage> images, Boolean likeExists) {
        this.productId = productId;
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

    public static ProductResponse of(ProductDto productDto, List<FileResponse> files) {
        List<ProductOption> options = productDto.getProductOptions().stream().map(option -> ProductOption.from(option))
                .collect(Collectors.toList());

        Map<Long, FileResponse> fileResponseMap = files.stream()
                .collect(Collectors.toMap(file -> file.getFileId(), file -> file));

        List<ProductImage> images = productDto.getImages().stream()
                .map(imageDto -> ProductImage.of(fileResponseMap.get(imageDto.getFileId()), imageDto))
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

    @Getter
    @NoArgsConstructor
    public static class ProductImage {
        private Long productImageId; //상품 이미지 ID
        private boolean basicYn; //기본이미지 여부
        private String saveFileName; // 저장 파일명
        private String originFileName; // 원래 파일명
        private Long fileId; // 파일 ID
        private String path; // 파일 경로

        @Builder
        public ProductImage(Long productImageId, boolean basicYn, String saveFileName,
                            String originFileName, Long fileId, String path) {
            this.productImageId = productImageId;
            this.basicYn = basicYn;
            this.saveFileName = saveFileName;
            this.originFileName = originFileName;
            this.fileId = fileId;
            this.path = path;
        }

        public static ProductImage of(FileResponse fileResponse, ProductImageDto productImageDto) {
            return ProductImage.builder()
                    .productImageId(productImageDto.getProductImageId())
                    .fileId(fileResponse.getFileId())
                    .basicYn(productImageDto.isBasicYn())
                    .saveFileName(fileResponse.getSaveFileName())
                    .originFileName(fileResponse.getOriginFileName())
                    .path(fileResponse.getFilePath())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ProductOption {
        private Long productOptionId; // 상품 옵션 ID
        private Size size; // 사이즈
        private Color color; // 색상
        private String etc; // 기타
        private Long additionalPrice; //추가금액
        private Long stockQuantity; //재고수량

        @Builder
        private ProductOption(Long productOptionId, Size size, Color color, String etc, Long additionalPrice, Long stockQuantity) {
            this.productOptionId = productOptionId;
            this.size = size;
            this.color = color;
            this.etc = etc;
            this.additionalPrice = additionalPrice;
            this.stockQuantity = stockQuantity;
        }

        public static ProductOption from(ProductOptionDto productOptionDto) {
            return ProductOption.builder()
                    .productOptionId(productOptionDto.getProductOptionId())
                    .size(productOptionDto.getSize())
                    .color(productOptionDto.getColor())
                    .etc(productOptionDto.getEtc())
                    .additionalPrice(productOptionDto.getAdditionalPrice())
                    .stockQuantity(productOptionDto.getStockQuantity())
                    .build();
        }
    }

}
