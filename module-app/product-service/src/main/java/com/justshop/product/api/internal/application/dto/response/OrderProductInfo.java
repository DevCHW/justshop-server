package com.justshop.product.api.internal.application.dto.response;

import com.justshop.product.domain.entity.ProductOption;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderProductInfo {

    private Long productOptionId; // 상품 옵션 ID
    private Long productId; // 상품 ID
    private Integer price; // 가격

    private int additionalPrice; // 추가금액
    private int stockQuantity; //재고수량

    @Builder
    public OrderProductInfo(Long productOptionId, Long productId, Integer price, int additionalPrice, int stockQuantity) {
        this.productOptionId = productOptionId;
        this.productId = productId;
        this.price = price;
        this.additionalPrice = additionalPrice;
        this.stockQuantity = stockQuantity;
    }

    public static OrderProductInfo from(ProductOption productOption) {
        return OrderProductInfo.builder()
                .productOptionId(productOption.getId())
                .productId(productOption.getProduct().getId())
                .price(productOption.getProduct().getPrice())
                .additionalPrice(productOption.getAdditionalPrice())
                .stockQuantity(productOption.getStockQuantity())
                .build();
    }
}
