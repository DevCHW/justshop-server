package com.justshop.product.domain.repository.querydsl;

import com.justshop.product.domain.entity.Product;
import com.justshop.product.domain.repository.querydsl.dto.ProductDto;
import com.justshop.product.domain.repository.querydsl.dto.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface QueryDslRepository {
    ProductDto findProductDto(Long productId);

    Page<Product> findProductsPageBy(SearchCondition searchCondition, Pageable pageable);
}
