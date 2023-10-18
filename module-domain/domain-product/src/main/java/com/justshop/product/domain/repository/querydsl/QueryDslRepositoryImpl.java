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
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.justshop.product.domain.entity.QProduct.*;
import static com.justshop.product.domain.entity.QProductDetail.*;
import static com.justshop.product.domain.entity.QProductImage.*;
import static com.justshop.product.domain.entity.QProductOption.*;
import static com.justshop.product.domain.repository.querydsl.dto.ProductDto.*;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class QueryDslRepositoryImpl implements QueryDslRepository {

    private final JPAQueryFactory queryFactory;

    // TODO : 테스트 작성
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

    // TODO : 테스트 작성
    @Override
    public Page<Product> findProductsPageBy(SearchCondition searchCondition, Pageable pageable) {
        System.out.println(searchCondition);
        System.out.println(pageable);

        List<Product> content = queryFactory.selectFrom(product)
                .where(
                        nameEq(searchCondition.getName()),
                        priceBetween(searchCondition.getMinPrice(), searchCondition.getMinPrice()),
                        statusEq(searchCondition.getStatus()),
                        genderEq(searchCondition.getGender()))
                .orderBy(createProductOrderSpecifiers(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(product.countDistinct())
                .from(product)
                .where(
                        nameEq(searchCondition.getName()),
                        priceBetween(searchCondition.getMinPrice(), searchCondition.getMinPrice()),
                        statusEq(searchCondition.getStatus()),
                        genderEq(searchCondition.getGender()))
                .fetchOne();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression nameEq(String name) {
        return hasText(name)? product.name.eq(name) : null;
    }

    private BooleanExpression priceBetween(Integer minPrice, Integer maxPrice) {
        return minPrice != null && maxPrice != null ? product.price.between(minPrice, maxPrice) : null;
    }

    private BooleanExpression statusEq(SellingStatus status) {
        return status != null ? product.status.eq(status) : null;
    }

    private BooleanExpression genderEq(Gender gender) {
        return gender != null ? product.gender.eq(gender) : null;
    }

    /* 정렬 */
    private OrderSpecifier[] createProductOrderSpecifiers(Pageable pageable) {
        Sort sort = pageable.getSort();
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

        List<OrderSpecifier> productOrderSpecifiers = sort.get()
                .map(o -> {
                    Order order = o.isAscending() ? Order.ASC : Order.DESC;
                    String property = o.getProperty();
                    PathBuilder<Product> pathBuilder = new PathBuilder<>(Product.class, "product");
                    return new OrderSpecifier(order, pathBuilder.getString(property));
                }).collect(Collectors.toList());

        orderSpecifiers.addAll(productOrderSpecifiers);
        return orderSpecifiers.toArray(OrderSpecifier[]::new);
    }

}
