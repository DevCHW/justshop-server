package com.justshop.product.domain.repository.querydsl.dto;

import com.justshop.product.domain.entity.enums.Gender;
import com.justshop.product.domain.entity.enums.SellingStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class SearchCondition {

    private String name;
    private Integer minPrice;
    private Integer maxPrice;
    private SellingStatus status;
    private Gender gender;

    @Builder
    public SearchCondition(String name, Integer minPrice, Integer maxPrice, SellingStatus status, Gender gender) {
        this.name = name;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.status = status;
        this.gender = gender;
    }

}
