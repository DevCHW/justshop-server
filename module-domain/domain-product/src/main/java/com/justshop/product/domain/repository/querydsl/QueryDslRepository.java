package com.justshop.product.domain.repository.querydsl;

import com.justshop.product.domain.repository.querydsl.dto.ProductDto;

public interface QueryDslRepository {
    ProductDto findProductDto(Long productId);
}
