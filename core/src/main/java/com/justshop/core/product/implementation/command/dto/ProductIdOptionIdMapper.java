package com.justshop.core.product.implementation.command.dto;

import lombok.Getter;

@Getter
public class ProductIdOptionIdMapper {

    private Long productId;
    private Long optionId;

    public ProductIdOptionIdMapper(Long productId, Long optionId) {
        this.productId = productId;
        this.optionId = optionId;
    }

}
