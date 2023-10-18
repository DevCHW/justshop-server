package com.justshop.product.api.external.presentation.dto.request;

import com.justshop.product.domain.entity.enums.Gender;
import com.justshop.product.domain.entity.enums.SellingStatus;
import lombok.Getter;

@Getter
public class ProductSearchCondition {

    private String name;
    private Integer price;
    private SellingStatus status;
    private Gender gender;

    public ProductSearchCondition(String name, Integer price, SellingStatus status, Gender gender) {
        this.name = name;
        this.price = price;
        this.status = status;
        this.gender = gender;
    }
}
