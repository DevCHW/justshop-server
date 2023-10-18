package com.justshop.product.domain.repository.querydsl.dto;

import com.justshop.product.domain.entity.enums.Gender;
import com.justshop.product.domain.entity.enums.SellingStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
public class SearchCondition {

    private String name;
    private SellingStatus status;
    private Gender gender;
    private Integer minPrice = 0;
    private Integer maxPrice = 2147483647;

    @Builder
    public SearchCondition(String name, Integer minPrice, Integer maxPrice, SellingStatus status, Gender gender) {
        this.name = name;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.status = status;
        this.gender = gender;
    }

}
