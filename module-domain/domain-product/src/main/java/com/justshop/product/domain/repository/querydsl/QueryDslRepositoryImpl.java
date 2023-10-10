package com.justshop.product.domain.repository.querydsl;

import com.justshop.product.domain.entity.*;
import com.justshop.product.domain.repository.querydsl.dto.ProductDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.justshop.product.domain.entity.QProduct.*;
import static com.justshop.product.domain.entity.QProductDetail.*;
import static com.justshop.product.domain.entity.QProductImage.*;
import static com.justshop.product.domain.entity.QProductOption.*;
import static com.justshop.product.domain.repository.querydsl.dto.ProductDto.*;

@RequiredArgsConstructor
public class QueryDslRepositoryImpl implements QueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    // TODO : 테스트 작성
    public ProductDto findProductDto(Long productId) {
        Product findProduct = queryFactory
                .selectFrom(product)
                .where(product.id.eq(productId))
                .join(product.productDetail, productDetail)
                .fetchJoin()
                .fetchOne();

        List<ProductImage> productImages = queryFactory
                .selectFrom(productImage)
                .where(productImage.product.id.eq(productId))
                .fetch();

        List<ProductOption> productOptions = queryFactory
                .selectFrom(productOption)
                .where(productOption.product.id.eq(productId))
                .fetch();

        List<ProductOptionDto> productOptionDtoList = productOptions.stream()
                .map(productOption -> ProductOptionDto.from(productOption))
                .collect(Collectors.toList());

        List<ProductImageDto> productImageDtoList = productImages.stream()
                .map(productImage -> ProductImageDto.from(productImage))
                .collect(Collectors.toList());

        return ProductDto.of(findProduct, productOptionDtoList, productImageDtoList);
    }

}
