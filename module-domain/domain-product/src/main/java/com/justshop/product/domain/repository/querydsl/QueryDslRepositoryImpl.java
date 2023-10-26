package com.justshop.product.domain.repository.querydsl;

import com.justshop.product.domain.entity.*;
import com.justshop.product.domain.entity.enums.Gender;
import com.justshop.product.domain.entity.enums.SellingStatus;
import com.justshop.product.domain.repository.querydsl.dto.ProductDto;
import com.justshop.product.domain.repository.querydsl.dto.SearchCondition;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.justshop.product.domain.entity.QProduct.*;
import static com.justshop.product.domain.entity.QProductCategory.*;
import static com.justshop.product.domain.entity.QProductDetail.*;
import static com.justshop.product.domain.entity.QProductImage.*;
import static com.justshop.product.domain.entity.QProductOption.*;
import static com.justshop.product.domain.repository.querydsl.dto.ProductDto.*;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class QueryDslRepositoryImpl implements QueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
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

    @Override
    public Page<Product> findProductsPageBy(SearchCondition searchCondition, Pageable pageable) {
        List<Product> content = queryFactory
                .selectFrom(product)
                .where(
                        nameContains(searchCondition.getName()),
                        priceBetween(searchCondition.getMinPrice(), searchCondition.getMaxPrice()),
                        statusEq(searchCondition.getStatus()),
                        genderEq(searchCondition.getGender()))
                .orderBy(createProductOrderSpecifiers(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(product.countDistinct())
                .from(product)
                .where(
                        nameContains(searchCondition.getName()),
                        priceBetween(searchCondition.getMinPrice(), searchCondition.getMinPrice()),
                        statusEq(searchCondition.getStatus()),
                        genderEq(searchCondition.getGender())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    /* 카테고리별 검색 조회 페이징*/
    @Override
    public Page<ProductCategory> findProductsPageByCategoryId(Long categoryId, SearchCondition searchCondition, Pageable pageable) {
        List<ProductCategory> content = queryFactory
                .select(productCategory)
                .from(productCategory)
                .join(productCategory.product, product)
                .fetchJoin()
                .where(
                        productCategory.categoryId.eq(categoryId),
                        nameContains(searchCondition.getName()),
                        priceBetween(searchCondition.getMinPrice(), searchCondition.getMaxPrice()),
                        statusEq(searchCondition.getStatus()),
                        genderEq(searchCondition.getGender()))
                .orderBy(createProductOrderSpecifiers(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(productCategory.count())
                .from(productCategory)
                .join(productCategory.product, product)
                .where(
                        productCategory.categoryId.eq(categoryId),
                        nameContains(searchCondition.getName()),
                        priceBetween(searchCondition.getMinPrice(), searchCondition.getMinPrice()),
                        statusEq(searchCondition.getStatus()),
                        genderEq(searchCondition.getGender())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    // == 동적쿼리 조건절 == //
    private BooleanExpression nameContains(String name) {
        return hasText(name)? product.name.contains(name) : null;
    }

    private BooleanExpression priceBetween(Integer minPrice, Integer maxPrice) {
        return minPrice != null && maxPrice != null ? product.price.between(minPrice, maxPrice): null;
    }

    private BooleanExpression statusEq(SellingStatus status) {
        return status != null ? product.status.eq(status) : null;
    }

    private BooleanExpression genderEq(Gender gender) {
        return gender != null ? product.gender.eq(gender) : null;
    }

    // == 정렬 == //
    private OrderSpecifier[] createProductOrderSpecifiers(Pageable pageable) {
        Sort sort = pageable.getSort();
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        List<OrderSpecifier> productOrderSpecifiers = sort.get()
                .map(order -> {
                    Order direction = order.isAscending() ? Order.ASC : Order.DESC;
                    String property = order.getProperty();
                    PathBuilder<Product> pathBuilder = new PathBuilder<>(Product.class, "product");
                    return new OrderSpecifier(direction, pathBuilder.getString(property));
                }).collect(Collectors.toList());

        orderSpecifiers.addAll(productOrderSpecifiers);
        orderSpecifiers.add(new OrderSpecifier(Order.DESC, product.createdDateTime));
        return orderSpecifiers.toArray(OrderSpecifier[]::new);
    }

}
